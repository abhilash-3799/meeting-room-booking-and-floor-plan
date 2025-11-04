package com.ait.mrb_fp.exception;

/**
 * Thrown when an employee with a specific ID doesn’t exist.
 * Example: /api/employees/E123 → E123 not found.
 */
public class EmployeeNotFoundException extends ResourceNotFoundException {
    public EmployeeNotFoundException(String employeeId) {
        super("Employee not found with ID: " + employeeId);
    }
}
