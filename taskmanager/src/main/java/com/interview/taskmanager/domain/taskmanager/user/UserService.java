package com.interview.taskmanager.domain.taskmanager.user;

import com.interview.taskmanager.domain.exception.IncorrectPasswordRuntimeException;

import java.util.List;

public class UserService {

    private final UserGateway userGateway;

    private final AvatarStorage avatarStorage;

    private final PasswordEncryptor passwordEncryptor;

    public UserService(UserGateway userGateway, AvatarStorage avatarStorage, PasswordEncryptor passwordEncryptor) {
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

    public void updateEmail() {
        throw new UnsupportedOperationException();
    }

    public void updateAvatar(Byte[] image, Integer currentUserId) {
        String oldAvatarUrl = userGateway.getUserAvatarUrl(currentUserId);
        if (oldAvatarUrl.equals(avatarStorage.getDefaultAvatarImgUrl())) {
            String newAvatarUrl = avatarStorage.uploadAvatarImg(image);
            userGateway.updateUserAvatarUrl(newAvatarUrl, currentUserId);
        } else {
            avatarStorage.deleteAvatarImg(oldAvatarUrl);
            String newAvatarUrl = avatarStorage.uploadAvatarImg(image);
            userGateway.updateUserAvatarUrl(newAvatarUrl, currentUserId);
        }
    }

    public void updateUsername(String newUsername, Integer userId) {
        userGateway.updateUsername(newUsername, userId);
    }

    public void updatePassword(Character[] oldPassword, Character[] newPassword, Integer userId) {
        String currentPassword = userGateway.getPassword(userId);
        String encryptedOldUserPassword = passwordEncryptor.encryptPassword(oldPassword);
        if (!currentPassword.equals(encryptedOldUserPassword)) {
            throw new IncorrectPasswordRuntimeException("Incorrect password");
        }
        String encryptedNewPassword = passwordEncryptor.encryptPassword(newPassword);
        userGateway.updatePassword(encryptedNewPassword, userId);
    }

    public void remove(Integer userId) {
        userGateway.remove(userId);
    }

    public ProfileDto getUserProfile(Integer userId) {
        return userGateway.getUserProfile();
    }

    public List<BriefUserInfo> getUsersByUsername(String username, Integer pageNumber) {
        final Integer PAGE_SIZE = 20;
        return userGateway.getUsersByUsername(username, pageNumber, PAGE_SIZE);
    }

}
