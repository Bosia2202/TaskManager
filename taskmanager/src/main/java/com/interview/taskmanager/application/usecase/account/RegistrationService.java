package com.interview.taskmanager.application.usecase.account;

import com.interview.taskmanager.application.dto.SignUp;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.AvatarPort;
import com.interview.taskmanager.application.ports.out.UserPort;
import com.interview.taskmanager.domain.User;
import com.interview.taskmanager.infra.exception.UserAlreadyExistRuntimeException;

public class RegistrationService {

    private final AvatarPort avatarPort;

    private final SecurityPort securityPort;

    private final UserPort userPort;

    public RegistrationService(AvatarPort avatarPort, SecurityPort securityPort, UserPort userPort) {
        this.avatarPort = avatarPort;
        this.securityPort = securityPort;
        this.userPort = userPort;
    }

    public void registration(SignUp signUp) {
        String email = signUp.email();
        String defaultAvatarUrl = avatarPort.getDefaultAvatarImgUrl();
        String username = signUp.username();
        String password = securityPort.encryptPassword(signUp.password());
        User user = new User(email, defaultAvatarUrl, username, password);
        if (userPort.findByEmail(email).isPresent()) {
            String message = "User with email already exist";
            throw new UserAlreadyExistRuntimeException(message);
        }
        userPort.create(user, "USER");
    }
}
