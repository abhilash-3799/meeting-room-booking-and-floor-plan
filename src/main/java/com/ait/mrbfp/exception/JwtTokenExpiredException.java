package com.ait.mrb_fp.exception;

/**
 *  Thrown when JWT token is expired, malformed, or invalid.
 * Example: Accessing protected endpoint with expired token.
 */
public class JwtTokenExpiredException extends UnauthorizedException {
    public JwtTokenExpiredException() {
        super("JWT token has expired or is invalid.");
    }
}
