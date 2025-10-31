package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.EmployeeRequestDTO;
import com.ait.mrb_fp.dto.response.EmployeeResponseDTO;
import com.ait.mrb_fp.entity.Employee;
import com.ait.mrb_fp.entity.Office;
import com.ait.mrb_fp.entity.Team;
import com.ait.mrb_fp.entity.Shift;
import com.ait.mrb_fp.exception.*;
import com.ait.mrb_fp.mapper.EmployeeMapper;
import com.ait.mrb_fp.repository.EmployeeRepository;
import com.ait.mrb_fp.repository.OfficeRepository;
import com.ait.mrb_fp.repository.TeamRepository;
import com.ait.mrb_fp.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        try {
            if (dto == null)
                throw new BadRequestException("Employee request body cannot be null.");
            if (dto.getEmail() == null || dto.getEmail().isBlank())
                throw new MissingRequestParameterException("Email is required.");
            if (dto.getFirstName() == null || dto.getFirstName().isBlank())
                throw new MissingRequestParameterException("First name is required.");

            if (employeeRepository.existsByEmail(dto.getEmail())) {
                throw new EmailAlreadyExistsException("Email already exists: " + dto.getEmail());
            }

            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new EmployeeNotFoundException("Team not found with ID: " + dto.getTeamId()));

            Office office = officeRepository.findById(dto.getOfficeId())
                    .orElseThrow(() -> new EmployeeNotFoundException("Office not found with ID: " + dto.getOfficeId()));

            Shift shift = null;
            if (dto.getShiftId() != null) {
                shift = shiftRepository.findById(dto.getShiftId())
                        .orElseThrow(() -> new EmployeeNotFoundException("Shift not found with ID: " + dto.getShiftId()));
            }

            try {
                Employee.EmployeeType.valueOf(dto.getEmployeeType());
            } catch (IllegalArgumentException ex) {
                throw new BadRequestException("Invalid employee type: " + dto.getEmployeeType());
            }

            boolean duplicateEmployee = employeeRepository.existsByFirstNameAndLastNameAndTeam(
                    dto.getFirstName(), dto.getLastName(), team);
            if (duplicateEmployee) {
                throw new DuplicateResourceException("Employee with same name already exists in this team.");
            }

            Employee employee = EmployeeMapper.toEntity(dto, team, office, shift);
            employee.setEmployeeId("EMP" + System.currentTimeMillis());

            Employee saved = employeeRepository.save(employee);
            return EmployeeMapper.toResponse(saved);


        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error while saving employee: " + ex.getMessage());
        } catch (TransactionSystemException ex) {
            throw new TransactionFailedException("Transaction failed while creating employee.");
        }
    }



    @Override
    public EmployeeResponseDTO getEmployeeById(String employeeId) {
        if (employeeId == null || employeeId.isBlank())
            throw new MissingRequestParameterException("Employee ID must not be empty.");

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

        return EmployeeMapper.toResponse(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        try {
            return employeeRepository.findByIsActiveTrue()
                    .stream()
                    .map(EmployeeMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseException("Error fetching employees from database.");
        } catch (Exception ex) {
            throw new InternalServerException("Unexpected error while fetching employees.");
        }
    }

    @Override
    public EmployeeResponseDTO updateEmployee(String employeeId, EmployeeRequestDTO dto) {
        try {
            if (employeeId == null || employeeId.isBlank())
                throw new MissingRequestParameterException("Employee ID is required.");

            Employee existing = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

            Employee duplicate = employeeRepository.findByEmail(dto.getEmail());
            if (duplicate != null && !duplicate.getEmployeeId().equals(employeeId)) {
                throw new EmailAlreadyExistsException("Email already exists: " + dto.getEmail());
            }

            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new EmployeeNotFoundException("Team not found with ID: " + dto.getTeamId()));
            Office office = officeRepository.findById(dto.getOfficeId())
                    .orElseThrow(() -> new EmployeeNotFoundException("Office not found with ID: " + dto.getOfficeId()));

            Shift shift = null;
            if (dto.getShiftId() != null) {
                shift = shiftRepository.findById(dto.getShiftId())
                        .orElseThrow(() -> new EmployeeNotFoundException("Shift not found with ID: " + dto.getShiftId()));
            }

            existing.setFirstName(dto.getFirstName());
            existing.setLastName(dto.getLastName());
            existing.setEmail(dto.getEmail());
            existing.setTeam(team);
            existing.setOffice(office);
            existing.setShift(shift);
            existing.setTeamLead(dto.isTeamLead());

            try {
                existing.setEmployeeType(Employee.EmployeeType.valueOf(dto.getEmployeeType()));
            } catch (IllegalArgumentException ex) {
                throw new BadRequestException("Invalid employee type: " + dto.getEmployeeType());
            }

            return EmployeeMapper.toResponse(employeeRepository.save(existing));

        } catch (DataAccessException ex) {
            throw new DatabaseException("Error updating employee: " + ex.getMessage());
        } catch (TransactionSystemException ex) {
            throw new TransactionFailedException("Transaction failed while updating employee.");
        } catch (Exception ex) {
            throw new InternalServerException("Unexpected error while updating employee: " + ex.getMessage());
        }
    }


    @Override
    public void deactivateEmployee(String employeeId) {
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

            if (!employee.isActive()) {
                throw new InvalidStateException("Employee is already inactive.");
            }

            employee.setActive(false);
            employeeRepository.save(employee);

        } catch (DataAccessException ex) {
            throw new DatabaseException("Error deactivating employee: " + ex.getMessage());
        } catch (Exception ex) {
            throw new InternalServerException("Unexpected error while deactivating employee.");
        }
    }
}
