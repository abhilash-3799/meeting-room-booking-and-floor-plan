package com.ait.mrb_fp.exception;

/**
 * Thrown when trying to register an employee/user with an existing email.
 * Example: Register API → "rabiya@example.com" already in use.
 */
public class EmailAlreadyExistsException extends DuplicateResourceException {
    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}
