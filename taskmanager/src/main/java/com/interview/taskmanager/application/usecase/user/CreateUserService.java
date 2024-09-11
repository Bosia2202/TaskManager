package com.interview.taskmanager.application.usecase.user;

import com.interview.taskmanager.application.dto.NewUserDto;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.AvatarPort;
import com.interview.taskmanager.application.ports.out.UserPort;
import com.interview.taskmanager.domain.User;
import com.interview.taskmanager.infra.exception.UserAlreadyExistRuntimeException;

public class CreateUserService {

    private final UserPort userPort;

    private final AvatarPort avatarPort;

    private final SecurityPort securityPort;

    public CreateUserService(UserPort userPort, AvatarPort avatarPort, SecurityPort securityPort) {
        this.userPort = userPort;
        this.avatarPort = avatarPort;
        this.securityPort = securityPort;
    }

    public void createDefaultUser(NewUserDto newUserDto) {
        String email = newUserDto.email();
        String defaultAvatarUrl = avatarPort.getDefaultAvatarImgUrl();
        String username = newUserDto.username();
        String password = securityPort.encryptPassword(newUserDto.password());
        User user = new User(email, defaultAvatarUrl, username, password);
        if (userPort.findByEmail(email).isPresent()) {
            String message = "User with email already exist";
            throw new UserAlreadyExistRuntimeException(message);
        }
        userPort.create(user);
    }
}
