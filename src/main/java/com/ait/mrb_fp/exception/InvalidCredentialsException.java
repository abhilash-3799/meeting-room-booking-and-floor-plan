package com.ait.mrb_fp.exception;

/**
 * Thrown when login credentials are incorrect.
 * Example: Wrong email/password combination during login.
 */
public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException() {
        super("Invalid username or password.");
    }
}
