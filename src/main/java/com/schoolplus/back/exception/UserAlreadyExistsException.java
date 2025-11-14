package com.schoolplus.back.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User with the given email or username already exists.");
    }
}
