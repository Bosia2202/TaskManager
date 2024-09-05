package com.interview.taskmanager.domain.user;

public interface PasswordEncryptor {
    String encryptPassword(char[] password);
}
