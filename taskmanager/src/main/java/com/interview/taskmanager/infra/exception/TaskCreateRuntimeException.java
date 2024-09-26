package com.interview.taskmanager.infra.exception;

public class TaskCreateRuntimeException extends RuntimeException {
    
    public TaskCreateRuntimeException(String message) {
        super(message);
    }

}
