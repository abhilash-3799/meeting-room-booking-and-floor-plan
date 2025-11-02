package com.ait.mrbfp.exception;

public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException() {
        super("Invalid username or password.");
    }
}
