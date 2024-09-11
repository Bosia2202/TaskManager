package com.interview.taskmanager.infra.exception;

public class IncorrectPasswordRuntimeException extends RuntimeException {
  public IncorrectPasswordRuntimeException(String message) {
    super(message);
  }
}
