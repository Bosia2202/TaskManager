package com.interview.taskmanager.infra.exception;

public class TaskNotFoundRuntimeException extends RuntimeException {
    
    public TaskNotFoundRuntimeException(String message) {
        super(message);
    }
}
