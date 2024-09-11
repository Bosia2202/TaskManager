package com.interview.taskmanager.application.ports.in;

public interface SecurityPort {

    Integer getCurrentUserId();

    String getCurrentUsername();

    String encryptPassword(char[] password);

}
