package com.ait.mrbfp.exception;

public class JwtTokenExpiredException extends UnauthorizedException {
    public JwtTokenExpiredException() {
        super("JWT token has expired or is invalid.");
    }
}
