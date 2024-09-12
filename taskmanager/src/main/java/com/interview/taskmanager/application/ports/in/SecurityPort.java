package com.interview.taskmanager.application.ports.in;

import com.interview.taskmanager.application.dto.DatabaseUserDto;

public interface SecurityPort {

    Integer getCurrentUserId();

    String getCurrentUsername();

    String encryptPassword(char[] password);

    boolean authentication(DatabaseUserDto databaseUser);

    String generateToken();

}
