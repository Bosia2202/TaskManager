package com.interview.taskmanager.domain.user;

import com.interview.taskmanager.domain.exception.IncorrectPasswordRuntimeException;

public class UserUpdateService {

    private final UserGateway userGateway;

    private final AvatarStorage avatarStorage;

    private final PasswordEncryptor passwordEncryptor;

    public UserUpdateService(UserGateway userGateway, AvatarStorage avatarStorage, PasswordEncryptor passwordEncryptor) {
        this.userGateway = userGateway;
        this.avatarStorage = avatarStorage;
        this.passwordEncryptor = passwordEncryptor;
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

}
