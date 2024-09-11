package com.interview.taskmanager.infra.exception;

public class UserNotFoundRuntimeException extends RuntimeException {
    public UserNotFoundRuntimeException(String message) {
        super(message);
    }
}
