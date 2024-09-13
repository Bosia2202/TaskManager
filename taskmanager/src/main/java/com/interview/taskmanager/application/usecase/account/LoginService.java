package com.interview.taskmanager.application.usecase.account;

import com.interview.taskmanager.application.dto.SignIn;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.infra.exception.AuthenticationRuntimeException;

public class LoginService {

    private final SecurityPort securityPort;

    public LoginService(SecurityPort securityPort) {
        this.securityPort = securityPort;
    }

    public String login(SignIn signIn) {
        if (!securityPort.authentication(signIn)) {
            throw new AuthenticationRuntimeException("Wrong email or password");
        }
        return securityPort.generateToken();
    }
}
