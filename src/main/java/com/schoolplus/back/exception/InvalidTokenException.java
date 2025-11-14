package com.schoolplus.back.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Expired or invalid token!");
    }
}
