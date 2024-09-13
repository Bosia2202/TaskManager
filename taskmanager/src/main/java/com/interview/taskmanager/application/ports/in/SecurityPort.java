package com.interview.taskmanager.application.ports.in;

import com.interview.taskmanager.application.dto.SignIn;

public interface SecurityPort {

    Integer getCurrentUserId();

    String getCurrentUsername();

    String encryptPassword(char[] password);

    boolean authentication(SignIn signIn);

    String generateToken();

}
