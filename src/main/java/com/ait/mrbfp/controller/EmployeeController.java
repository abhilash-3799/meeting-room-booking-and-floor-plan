package com.ait.mrbfp.controller;


import com.ait.mrbfp.dto.request.EmployeeRequestDTO;
import com.ait.mrbfp.dto.response.EmployeeResponseDTO;
import com.ait.mrbfp.service.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @Operation(summary = "Create a new employee")
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
        log.info("Received request to create employee: {}", dto.getEmail());
        return ResponseEntity.ok(employeeServiceImpl.createEmployee(dto));
    }

    @Operation(summary = "Get all employees")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeServiceImpl.getAllEmployees());
    }

    @Operation(summary = "Get employee by ID")
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeServiceImpl.getEmployeeById(employeeId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee details")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable String id, @Valid @RequestBody EmployeeRequestDTO dto) {
        return ResponseEntity.ok(employeeServiceImpl.updateEmployee(id, dto));
    }

    @Operation(summary = "Delete employee by ID")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
        employeeServiceImpl.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}
