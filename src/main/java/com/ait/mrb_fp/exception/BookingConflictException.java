package com.ait.mrb_fp.exception;

/**
 * Thrown when a booking overlaps or conflicts with an existing one.
 * Example: Two users booking same room & time slot.
 */
public class BookingConflictException extends DuplicateResourceException {
    public BookingConflictException(String message) {
        super("Booking conflict detected: " + message);
    }
}
