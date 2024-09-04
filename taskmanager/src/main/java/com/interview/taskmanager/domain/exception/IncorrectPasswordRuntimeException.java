package com.interview.taskmanager.domain.exception;

public class IncorrectPasswordRuntimeException extends RuntimeException {
  public IncorrectPasswordRuntimeException(String message) {
    super(message);
  }
}
