package com.interview.taskmanager.infra.exception;

public class AuthenticationRuntimeException extends RuntimeException {

    public AuthenticationRuntimeException(String message) {
        super(message);
    }

}
