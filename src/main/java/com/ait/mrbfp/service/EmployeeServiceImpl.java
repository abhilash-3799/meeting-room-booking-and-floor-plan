package com.ait.mrbfp.service;

import com.ait.mrbfp.dto.request.EmployeeRequestDTO;
import com.ait.mrbfp.dto.response.EmployeeResponseDTO;
import com.ait.mrbfp.entity.Employee;
import com.ait.mrbfp.entity.Office;
import com.ait.mrbfp.entity.Shift;
import com.ait.mrbfp.entity.Team;
import com.ait.mrbfp.exception.BadRequestException;
import com.ait.mrbfp.exception.ResourceNotFoundException;
import com.ait.mrbfp.mapper.EmployeeMapper;
import com.ait.mrbfp.repository.EmployeeRepository;
import com.ait.mrbfp.repository.OfficeRepository;
import com.ait.mrbfp.repository.ShiftRepository;
import com.ait.mrbfp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;
    private final OfficeRepository officeRepository;
    private final ShiftRepository shiftRepository;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        log.info("Creating employee: {}", requestDTO.getEmail());

        if (employeeRepository.existsByEmail(requestDTO.getEmail())) {
            log.error("Email already exists: {}", requestDTO.getEmail());
            throw new BadRequestException("Email already exists: " + requestDTO.getEmail());
        }

        Team team = teamRepository.findById(requestDTO.getTeamId())
                .orElseThrow(() -> new BadRequestException("Invalid team ID: " + requestDTO.getTeamId()));

        Office office = officeRepository.findById(requestDTO.getOfficeId())
                .orElseThrow(() -> new BadRequestException("Invalid office ID: " + requestDTO.getOfficeId()));

        Shift shift = null;
        if (requestDTO.getShiftId() != null) {
            shift = shiftRepository.findById(requestDTO.getShiftId())
                    .orElseThrow(() -> new BadRequestException("Invalid shift ID: " + requestDTO.getShiftId()));
        }

        Employee employee = EmployeeMapper.toEntity(requestDTO, team, office, shift);
        employee = employeeRepository.save(employee);

        log.info("Employee created successfully with ID: {}", employee.getEmployeeId());
        return EmployeeMapper.toResponse(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        log.info("Fetching all employees...");
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(String employeeId) {
        log.info("Fetching employee with ID: {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BadRequestException("Employee not found with ID: " + employeeId));
        return EmployeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(String employeeId, EmployeeRequestDTO requestDTO) {
        log.info("Updating employee with ID: {}", employeeId);

        Employee existing = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BadRequestException("Employee not found with ID: " + employeeId));

        if (!existing.getEmail().equals(requestDTO.getEmail()) &&
                employeeRepository.existsByEmail(requestDTO.getEmail())) {
            log.error("Email already exists: {}", requestDTO.getEmail());
            throw new BadRequestException("Email already exists: " + requestDTO.getEmail());
        }

        Team team = teamRepository.findById(requestDTO.getTeamId())
                .orElseThrow(() -> new BadRequestException("Invalid team ID: " + requestDTO.getTeamId()));

        Office office = officeRepository.findById(requestDTO.getOfficeId())
                .orElseThrow(() -> new BadRequestException("Invalid office ID: " + requestDTO.getOfficeId()));

        Shift shift = null;
        if (requestDTO.getShiftId() != null) {
            shift = shiftRepository.findById(requestDTO.getShiftId())
                    .orElseThrow(() -> new BadRequestException("Invalid shift ID: " + requestDTO.getShiftId()));
        }

        EmployeeMapper.updateEntity(existing, requestDTO, team, office, shift);
        employeeRepository.save(existing);

        log.info("Employee updated successfully with ID: {}", existing.getEmployeeId());
        return EmployeeMapper.toResponse(existing);
    }

    @Override
    @Transactional
    public void deleteEmployee(String employeeId) {
        log.warn("Soft deleting employee with ID: {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employee.setActive(false);
        employeeRepository.save(employee);
        log.info("Employee marked as inactive successfully: {}", employeeId);
    }
}
