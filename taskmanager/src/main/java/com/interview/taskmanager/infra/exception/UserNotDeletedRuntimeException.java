package com.interview.taskmanager.infra.exception;

public class UserNotDeletedRuntimeException extends RuntimeException {
    
    public UserNotDeletedRuntimeException(String message) {
        super(message);
    }
}
