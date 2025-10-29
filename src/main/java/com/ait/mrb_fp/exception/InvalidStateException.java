package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ⚠️ Thrown when the system is in an invalid or unexpected state for the requested operation.
 * Example: Trying to cancel a booking that is already completed.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidStateException extends RuntimeException {
    public InvalidStateException(String message) {
        super("Invalid state: " + message);
    }
}
