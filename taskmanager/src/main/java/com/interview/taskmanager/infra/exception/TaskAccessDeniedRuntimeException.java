package com.interview.taskmanager.infra.exception;

public class TaskAccessDeniedRuntimeException extends RuntimeException {
    public TaskAccessDeniedRuntimeException(String message) {
        super(message);
    }
}
