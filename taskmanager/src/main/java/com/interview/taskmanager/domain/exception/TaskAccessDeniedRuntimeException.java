package com.interview.taskmanager.domain.exception;

public class TaskAccessDeniedRuntimeException extends RuntimeException {
    public TaskAccessDeniedRuntimeException(String message) {
        super(message);
    }
}
