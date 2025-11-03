package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown for database-related issues like constraint violations or failed queries.
 * Example: Foreign key constraint or transaction failure.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
