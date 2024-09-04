package com.interview.taskmanager.domain.user;

public class UserCreationService {

    private final UserGateway userGateway;

    private final AvatarStorage avatarStorage;

    private final PasswordEncryptor passwordEncryptor;

    public UserCreationService(AvatarStorage avatarStorage, PasswordEncryptor passwordEncryptor, UserGateway userGateway) {
        this.userGateway = userGateway;
        this.avatarStorage = avatarStorage;
        this.passwordEncryptor = passwordEncryptor;
    }

    public void create(CreateUserDto createUserDto) {
        String email = createUserDto.email();
        String defaultAvatarUrl = avatarStorage.getDefaultAvatarImgUrl();
        String username = createUserDto.username();
        String password = passwordEncryptor.encryptPassword(createUserDto.password());
        userGateway.create(email, defaultAvatarUrl, username, password, Role.USER);
    }
}
