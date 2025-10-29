package com.ait.mrb_fp.exception;

/**
 * ⚠️ Thrown when the shift ID provided does not exist.
 * Example: Setting employee shift to "S999" → not found.
 */
public class ShiftNotFoundException extends ResourceNotFoundException {
    public ShiftNotFoundException(String shiftId) {
        super("Shift not found with ID: " + shiftId);
    }
}
