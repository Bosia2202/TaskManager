package com.interview.taskmanager.infra.exception;

public class CommentNotFoundRuntimeException extends RuntimeException {

    public CommentNotFoundRuntimeException(String message) {
        super(message);
    }

}
