package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  Thrown when the request body is malformed or cannot be parsed as JSON.
 * Example: Invalid JSON format or wrong field types sent in POST request.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestBodyException extends RuntimeException {
    public InvalidRequestBodyException(String message) {
        super("Invalid request body: " + message);
    }
}
