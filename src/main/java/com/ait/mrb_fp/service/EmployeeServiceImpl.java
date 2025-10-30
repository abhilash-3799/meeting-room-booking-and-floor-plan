package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.EmployeeRequestDTO;
import com.ait.mrb_fp.dto.response.EmployeeResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.entity.Shift;
import com.ait.mrb_fp.exception.ResourceNotFoundException;
import com.ait.mrb_fp.mapper.EmployeeMapper;
import com.ait.mrb_fp.repository.EmployeeRepository;
import com.ait.mrb_fp.repository.OfficeRepository;
import com.ait.mrb_fp.repository.TeamRepository;
import com.ait.mrb_fp.repository.ShiftRepository;
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
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        log.info("Creating new employee: {}", dto.getFirstName());
        try {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + dto.getTeamId()));
            Office office = officeRepository.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + dto.getOfficeId()));
            Shift shift = null;
            if (dto.getShiftId() != null) {
                shift = shiftRepository.findById(dto.getShiftId())
                        .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + dto.getShiftId()));
            }

            Employee employee = EmployeeMapper.toEntity(dto, team, office, shift);
            employee.setEmployeeId("EMP" + System.currentTimeMillis());
            Employee saved = employeeRepository.save(employee);
            log.info("Employee created successfully with ID: {}", saved.getEmployeeId());
            return EmployeeMapper.toResponse(saved);
        } catch (Exception e) {
            log.error("Error creating employee: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(String employeeId) {
        log.info("Fetching employee by ID: {}", employeeId);
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
            return EmployeeMapper.toResponse(employee);
        } catch (Exception e) {
            log.error("Error fetching employee by ID {}: {}", employeeId, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        log.info("Fetching all active employees");
        try {
            return employeeRepository.findByIsActiveTrue()
                    .stream()
                    .map(EmployeeMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching employee list: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public EmployeeResponseDTO updateEmployee(String employeeId, EmployeeRequestDTO dto) {
        log.info("Updating employee with ID: {}", employeeId);
        try {
            Employee existing = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + dto.getTeamId()));
            Office office = officeRepository.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Office not found with ID: " + dto.getOfficeId()));
            Shift shift = null;
            if (dto.getShiftId() != null) {
                shift = shiftRepository.findById(dto.getShiftId())
                        .orElseThrow(() -> new ResourceNotFoundException("Shift not found with ID: " + dto.getShiftId()));
            }

            existing.setFirstName(dto.getFirstName());
            existing.setLastName(dto.getLastName());
            existing.setEmail(dto.getEmail());
            existing.setTeam(team);
            existing.setOffice(office);
            existing.setShift(shift);
            existing.setEmployeeType(Employee.EmployeeType.valueOf(dto.getEmployeeType()));

            Employee updated = employeeRepository.save(existing);
            log.info("Employee with ID {} updated successfully", employeeId);
            return EmployeeMapper.toResponse(updated);
        } catch (Exception e) {
            log.error("Error updating employee ID {}: {}", employeeId, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deactivateEmployee(String employeeId) {
        log.info("Deactivating employee with ID: {}", employeeId);
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
            employee.setActive(false);
            employeeRepository.save(employee);
            log.info("Employee with ID {} marked as inactive", employeeId);
        } catch (Exception e) {
            log.error("Error deactivating employee ID {}: {}", employeeId, e.getMessage());
            throw e;
        }
    }
}
