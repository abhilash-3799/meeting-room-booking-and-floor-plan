package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ⚠️ Generic fallback for unexpected server errors.
 * Example: NullPointerException or any uncaught error in controller/service.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}
