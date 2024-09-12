package com.interview.taskmanager.application.usecase.account;

import com.interview.taskmanager.application.dto.DatabaseUserDto;
import com.interview.taskmanager.application.dto.SignIn;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.UserPort;
import com.interview.taskmanager.infra.exception.AuthenticationRuntimeException;

public class LoginService {

    private final UserPort userPort;

    private final SecurityPort securityPort;

    public LoginService(UserPort userPort, SecurityPort securityPort) {
        this.userPort = userPort;
        this.securityPort = securityPort;
    }

    public String login(SignIn signIn) {
        DatabaseUserDto databaseUser = userPort.getUserByEmail(signIn.email());
        if (!securityPort.authentication(databaseUser)) {
            throw new AuthenticationRuntimeException("Wrong email or password");
        }
        return securityPort.generateToken();
    }
}
