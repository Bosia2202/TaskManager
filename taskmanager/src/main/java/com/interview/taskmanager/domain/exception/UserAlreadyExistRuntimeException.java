package com.interview.taskmanager.domain.exception;

public class UserAlreadyExistRuntimeException extends RuntimeException {
    public UserAlreadyExistRuntimeException(String message) {
        super(message);
    }
}
