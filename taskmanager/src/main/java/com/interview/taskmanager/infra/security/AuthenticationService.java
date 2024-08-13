package com.interview.taskmanager.infra.security;

import com.interview.taskmanager.infra.security.dto.SignInRequest;
import com.interview.taskmanager.infra.security.dto.SignUpRequest;

public interface AuthenticationService {

    void registrationUser(SignUpRequest signUpRequest);

    String authenticationUser(SignInRequest signInRequest);
}
