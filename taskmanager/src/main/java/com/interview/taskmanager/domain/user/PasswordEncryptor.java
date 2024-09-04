package com.interview.taskmanager.domain.user;

public interface PasswordEncryptor {
    String encryptPassword(Character[] password);
}
