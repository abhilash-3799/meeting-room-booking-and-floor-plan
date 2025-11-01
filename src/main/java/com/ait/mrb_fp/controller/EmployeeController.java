package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.EmployeeRequestDTO;
import com.ait.mrb_fp.dto.response.EmployeeResponseDTO;
import com.ait.mrb_fp.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Employee Management",
        description = "Endpoints for managing employee data such as creation, update, retrieval, and soft deletion."
)
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(
            summary = "Create a new employee",
            description = "Adds a new employee record to the system using the provided request DTO."
    )
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO dto) {
        log.info("Creating employee: {}", dto);
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get employee by ID",
            description = "Fetches detailed information of an employee using their unique identifier."
    )
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable String id) {
        log.info("Fetching employee by ID: {}", id);
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    @Operation(
            summary = "List all employees",
            description = "Retrieves a list of all employees currently stored in the system."
    )
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        log.info("Fetching all employees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update employee details",
            description = "Updates an existing employee’s data using their ID and the provided request DTO."
    )
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable String id, @RequestBody EmployeeRequestDTO dto) {
        log.info("Updating employee ID: {}", id);
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deactivate (soft delete) an employee",
            description = "Marks an employee as inactive instead of permanently deleting their record."
    )
    public ResponseEntity<String> deactivateEmployee(@PathVariable String id) {
        log.info("Deactivating employee ID: {}", id);
        employeeService.deactivateEmployee(id);
        return ResponseEntity.ok("Employee marked as inactive (soft deleted).");
    }
}
