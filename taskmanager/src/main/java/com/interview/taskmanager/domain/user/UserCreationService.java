package com.interview.taskmanager.domain.user;

import com.interview.taskmanager.domain.exception.UserAlreadyExistRuntimeException;

public class UserCreationService {

    private final UserGateway userGateway;

    private final AvatarStorage avatarStorage;

    private final PasswordEncryptor passwordEncryptor;

    public UserCreationService(UserGateway userGateway, AvatarStorage avatarStorage,
            PasswordEncryptor passwordEncryptor) {
        this.userGateway = userGateway;
        this.avatarStorage = avatarStorage;
        this.passwordEncryptor = passwordEncryptor;
    }

    public void createDefaultUser(CreateUserDto createUserDto) {
        String email = createUserDto.email();
        String defaultAvatarUrl = avatarStorage.getDefaultAvatarImgUrl();
        String username = createUserDto.username();
        String password = passwordEncryptor.encryptPassword(createUserDto.password());
        if (!userGateway.create(email, defaultAvatarUrl, username, password, Role.USER)) {
            throw buildUserAlreadyExistRuntimeException();
        }
    }

    private UserAlreadyExistRuntimeException buildUserAlreadyExistRuntimeException() {
        String message = "User with email already exist";
        return new UserAlreadyExistRuntimeException(message);
    }
}
