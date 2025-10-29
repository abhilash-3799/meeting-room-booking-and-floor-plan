package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ⚠️ Thrown when two users try to update or book the same resource simultaneously.
 * Example: Two employees try to book the same room at the same time.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConcurrentModificationException extends RuntimeException {
    public ConcurrentModificationException(String message) {
        super("Concurrent modification detected: " + message);
    }
}
