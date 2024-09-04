package com.interview.taskmanager.domain.taskmanager.user;

public interface PasswordEncryptor {
    String encryptPassword(Character[] password);
}
