package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ⚠️ Thrown when authentication fails (user not logged in or invalid credentials).
 * Example: Invalid JWT token or user tries to access a protected resource without login.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class gitUnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
