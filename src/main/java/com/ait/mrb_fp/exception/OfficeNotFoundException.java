package com.ait.mrb_fp.exception;

/**
 * ⚠️ Thrown when the specified office does not exist.
 * Example: Assigning employee to office ID that doesn’t exist.
 */
public class OfficeNotFoundException extends ResourceNotFoundException {
    public OfficeNotFoundException(String officeId) {
        super("Office not found with ID: " + officeId);
    }
}
