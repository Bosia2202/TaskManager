package com.interview.taskmanager.domain.exception;

public class CommentAccessDeniedRuntimeException extends RuntimeException {
    public CommentAccessDeniedRuntimeException(String message) {
        super(message);
    }
}
