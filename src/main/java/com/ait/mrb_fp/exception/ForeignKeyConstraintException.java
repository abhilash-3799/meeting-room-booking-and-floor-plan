package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  Thrown when attempting to delete a record that is still referenced by another table.
 * Example: Deleting an office that still has employees linked to it.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ForeignKeyConstraintException extends RuntimeException {
    public ForeignKeyConstraintException(String message) {
        super("Foreign key constraint violation: " + message);
    }
}
