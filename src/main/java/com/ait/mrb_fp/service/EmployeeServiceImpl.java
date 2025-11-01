package com.ait.mrb_fp.service;

import com.ait.mrb_fp.dto.request.EmployeeRequestDTO;
import com.ait.mrb_fp.dto.response.EmployeeResponseDTO;
import com.ait.mrb_fp.entity.*;
import com.ait.mrb_fp.exception.*;
import com.ait.mrb_fp.mapper.EmployeeMapper;
import com.ait.mrb_fp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;
    private final OfficeRepository officeRepository;
    private final ShiftRepository shiftRepository;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        log.info("Creating employee with email: {}", dto != null ? dto.getEmail() : "null");
        try {
            if (dto == null)
                throw new BadRequestException("Employee request body cannot be null.");
            if (dto.getEmail() == null || dto.getEmail().isBlank())
                throw new MissingRequestParameterException("Email is required.");
            if (dto.getFirstName() == null || dto.getFirstName().isBlank())
                throw new MissingRequestParameterException("First name is required.");

            if (employeeRepository.existsByEmail(dto.getEmail())) {
                log.warn("Duplicate email attempt: {}", dto.getEmail());
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

            boolean duplicateEmployee = employeeRepository.existsByFirstNameAndLastNameAndTeam(
                    dto.getFirstName(), dto.getLastName(), team);
            if (duplicateEmployee) {
                log.warn("Duplicate employee name in team: {} {}", dto.getFirstName(), dto.getLastName());
                throw new DuplicateResourceException("Employee with same name already exists in this team.");
            }

            Employee employee = EmployeeMapper.toEntity(dto, team, office, shift);
            employee.setEmployeeId("EMP" + System.currentTimeMillis());
            Employee saved = employeeRepository.save(employee);
            log.info("Employee created successfully with ID: {}", saved.getEmployeeId());
            return EmployeeMapper.toResponse(saved);
        } catch (DataAccessException ex) {
            log.error("Database error while saving employee: {}", ex.getMessage());
            throw new DatabaseException("Database error while saving employee: " + ex.getMessage());
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed while creating employee: {}", ex.getMessage());
            throw new TransactionFailedException("Transaction failed while creating employee.");
        } catch (Exception ex) {
            log.error("Unexpected error while creating employee: {}", ex.getMessage());
            throw new InternalServerException("Unexpected error while creating employee.");
        }
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(String employeeId) {
        log.info("Fetching employee with ID: {}", employeeId);
        try {
            if (employeeId == null || employeeId.isBlank())
                throw new MissingRequestParameterException("Employee ID must not be empty.");
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));
            log.info("Employee fetched successfully: {}", employeeId);
            return EmployeeMapper.toResponse(employee);
        } catch (DataAccessException ex) {
            log.error("Database error while fetching employee {}: {}", employeeId, ex.getMessage());
            throw new DatabaseException("Database error while fetching employee: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error while fetching employee {}: {}", employeeId, ex.getMessage());
            throw new InternalServerException("Unexpected error while fetching employee.");
        }
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        log.info("Fetching all active employees");
        try {
            List<EmployeeResponseDTO> employees = employeeRepository.findByIsActiveTrue()
                    .stream().map(EmployeeMapper::toResponse).collect(Collectors.toList());
            log.info("Fetched {} employees", employees.size());
            return employees;
        } catch (DataAccessException ex) {
            log.error("Database error while fetching employees: {}", ex.getMessage());
            throw new DatabaseException("Error fetching employees from database.");
        } catch (Exception ex) {
            log.error("Unexpected error while fetching employees: {}", ex.getMessage());
            throw new InternalServerException("Unexpected error while fetching employees.");
        }
    }

    @Override
    public EmployeeResponseDTO updateEmployee(String employeeId, EmployeeRequestDTO dto) {
        log.info("Updating employee with ID: {}", employeeId);
        try {
            if (employeeId == null || employeeId.isBlank())
                throw new MissingRequestParameterException("Employee ID is required.");

            Employee existing = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));

            Employee duplicate = employeeRepository.findByEmail(dto.getEmail());
            if (duplicate != null && !duplicate.getEmployeeId().equals(employeeId)) {
                log.warn("Duplicate email on update: {}", dto.getEmail());
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
            existing.setEmployeeType(Employee.EmployeeType.valueOf(dto.getEmployeeType()));

            Employee updated = employeeRepository.save(existing);
            log.info("Employee updated successfully with ID: {}", employeeId);
            return EmployeeMapper.toResponse(updated);
        } catch (DataAccessException ex) {
            log.error("Database error while updating employee {}: {}", employeeId, ex.getMessage());
            throw new DatabaseException("Error updating employee: " + ex.getMessage());
        } catch (TransactionSystemException ex) {
            log.error("Transaction failed while updating employee {}: {}", employeeId, ex.getMessage());
            throw new TransactionFailedException("Transaction failed while updating employee.");
        } catch (Exception ex) {
            log.error("Unexpected error while updating employee {}: {}", employeeId, ex.getMessage());
            throw new InternalServerException("Unexpected error while updating employee: " + ex.getMessage());
        }
    }

    @Override
    public void deactivateEmployee(String employeeId) {
        log.info("Deactivating employee with ID: {}", employeeId);
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));
            if (!employee.isActive()) {
                log.warn("Employee already inactive: {}", employeeId);
                throw new InvalidStateException("Employee is already inactive.");
            }
            employee.setActive(false);
            employeeRepository.save(employee);
            log.info("Employee deactivated successfully: {}", employeeId);
        } catch (DataAccessException ex) {
            log.error("Database error while deactivating employee {}: {}", employeeId, ex.getMessage());
            throw new DatabaseException("Error deactivating employee: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error while deactivating employee {}: {}", employeeId, ex.getMessage());
            throw new InternalServerException("Unexpected error while deactivating employee.");
        }
    }
}
