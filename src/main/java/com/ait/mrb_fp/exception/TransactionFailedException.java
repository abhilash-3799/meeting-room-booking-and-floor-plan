package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ⚠️ Thrown when a database transaction fails or rolls back unexpectedly.
 * Example: Saving employee + user_login together fails partially.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(String message) {
        super("Transaction failed: " + message);
    }
}
