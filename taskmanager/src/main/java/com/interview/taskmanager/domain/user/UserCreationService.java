package com.interview.taskmanager.domain.user;

public class UserCreationService {

    private final UserGateway userGateway;

    private final AvatarStorage avatarStorage;

    private final PasswordEncryptor passwordEncryptor;

    public UserCreationService(UserGateway userGateway, AvatarStorage avatarStorage, PasswordEncryptor passwordEncryptor) {
        this.userGateway = userGateway;
        this.avatarStorage = avatarStorage;
        this.passwordEncryptor = passwordEncryptor;
    }

    public void createDefaultUser(CreateUserDto createUserDto) {
        String email = createUserDto.email();
        String defaultAvatarUrl = avatarStorage.getDefaultAvatarImgUrl();
        String username = createUserDto.username();
        String password = passwordEncryptor.encryptPassword(createUserDto.password());
        userGateway.create(email, defaultAvatarUrl, username, password, Role.USER);
    }
}
