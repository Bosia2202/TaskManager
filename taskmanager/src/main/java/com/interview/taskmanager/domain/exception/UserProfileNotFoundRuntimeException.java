package com.interview.taskmanager.domain.exception;

public class UserProfileNotFoundRuntimeException extends RuntimeException {
    public UserProfileNotFoundRuntimeException(String message) {
        super(message);
    }
}
