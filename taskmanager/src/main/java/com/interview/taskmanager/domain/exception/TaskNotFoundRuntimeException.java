package com.interview.taskmanager.domain.exception;

public class TaskNotFoundRuntimeException extends RuntimeException {
    
    public TaskNotFoundRuntimeException(String message) {
        super(message);
    }
}
