package com.ait.mrb_fp.controller;

import com.ait.mrb_fp.dto.request.EmployeeRequestDTO;
import com.ait.mrb_fp.dto.response.EmployeeResponseDTO;
import com.ait.mrb_fp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // ---------------------------------------
    // ✅ Basic CRUD
    // ---------------------------------------

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO dto) {
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable String id,
            @RequestBody EmployeeRequestDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    // ✅ Soft Delete (Deactivate Employee)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateEmployee(@PathVariable String id) {
        employeeService.deactivateEmployee(id);
        return ResponseEntity.ok("Employee marked as inactive (soft deleted).");
    }


}
