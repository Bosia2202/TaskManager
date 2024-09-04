package com.interview.taskmanager.domain.user;

public class UserRemoveService {

    private final UserGateway userGateway;

    public UserRemoveService(UserGateway userGateway, AvatarStorage avatarStorage, PasswordEncryptor passwordEncryptor) {
        this.userGateway = userGateway;
    }

    public void remove(Integer userId) {
        userGateway.remove(userId);
    }

}
