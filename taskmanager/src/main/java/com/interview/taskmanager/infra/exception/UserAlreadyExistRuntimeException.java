package com.interview.taskmanager.infra.exception;

public class UserAlreadyExistRuntimeException extends RuntimeException {
    public UserAlreadyExistRuntimeException(String message) {
        super(message);
    }
}
