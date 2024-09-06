package com.interview.taskmanager.domain.exception;

public class UserDoesNotDeleteRuntimeException extends RuntimeException {
    
    public UserDoesNotDeleteRuntimeException(String message) {
        super(message);
    }
}
