package com.interview.taskmanager.domain.exception;

public class UserNotDeletedRuntimeException extends RuntimeException {
    
    public UserNotDeletedRuntimeException(String message) {
        super(message);
    }
}
