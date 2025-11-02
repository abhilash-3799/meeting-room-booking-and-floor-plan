package com.ait.mrbfp.exception;

public class BookingConflictException extends DuplicateResourceException {
    public BookingConflictException(String message) {
        super("Booking conflict detected: " + message);
    }
}
