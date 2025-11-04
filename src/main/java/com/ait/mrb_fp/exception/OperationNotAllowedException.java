package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  Thrown when an operation is not permitted due to business rules.
 * Example: Attempting to delete a permanent employee record manually.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class OperationNotAllowedException extends RuntimeException {
    public OperationNotAllowedException(String message) {
        super("Operation not allowed: " + message);
    }
}
