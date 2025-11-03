package com.ait.mrb_fp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  Thrown when an invalid enum value is passed in a request.
 * Example: EmployeeType = "TEMP" when valid values are PERMANENT or CONTRACT.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEnumValueException extends RuntimeException {
    public InvalidEnumValueException(String message) {
        super(message);
    }
}
