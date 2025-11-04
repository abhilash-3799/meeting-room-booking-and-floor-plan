package com.ait.mrbfp.exception;

public class EmailAlreadyExistsException extends DuplicateResourceException {
    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}
