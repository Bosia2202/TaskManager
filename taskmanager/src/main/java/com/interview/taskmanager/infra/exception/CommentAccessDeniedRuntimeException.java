package com.interview.taskmanager.infra.exception;

public class CommentAccessDeniedRuntimeException extends RuntimeException {
    public CommentAccessDeniedRuntimeException(String message) {
        super(message);
    }
}
