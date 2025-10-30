package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ⚠️ Thrown when a required query or path parameter is missing in the request.
 * Example: GET /api/employees?officeId= → officeId parameter not provided.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingRequestParameterException extends RuntimeException {
    public MissingRequestParameterException(String paramName) {
        super("Missing required request parameter: " + paramName);
    }
}
