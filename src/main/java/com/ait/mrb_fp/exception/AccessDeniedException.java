package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ⚠️ Thrown when a user is authenticated but not authorized for a specific resource/action.
 * Example: Employee with role USER tries to access ADMIN-only endpoint.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super("Access denied: " + message);
    }
}
