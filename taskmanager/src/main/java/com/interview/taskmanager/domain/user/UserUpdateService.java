package com.interview.taskmanager.domain.user;

import com.interview.taskmanager.domain.exception.IncorrectPasswordRuntimeException;
import com.interview.taskmanager.domain.security.IdentificationUserService;

public class UserUpdateService {

    private final UserGateway userGateway;

    private final AvatarStorage avatarStorage;

    private final IdentificationUserService identificationUserService;

    private final PasswordEncryptor passwordEncryptor;

    public UserUpdateService(UserGateway userGateway, AvatarStorage avatarStorage, IdentificationUserService identificationUserService, PasswordEncryptor passwordEncryptor) {
        this.userGateway = userGateway;
        this.avatarStorage = avatarStorage;
        this.identificationUserService = identificationUserService;
        this.passwordEncryptor = passwordEncryptor;
    }

    public void updateAvatar(byte[] image) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
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

    public void updateUsername(String newUsername) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        userGateway.updateUsername(newUsername, currentUserId);
    }

    public void updatePassword(char[] oldPassword, char[] newPassword) {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        String currentPassword = userGateway.getPassword(currentUserId);
        String encryptedOldUserPassword = passwordEncryptor.encryptPassword(oldPassword);
        if (!currentPassword.equals(encryptedOldUserPassword)) {
            throw new IncorrectPasswordRuntimeException("Incorrect password");
        }
        String encryptedNewPassword = passwordEncryptor.encryptPassword(newPassword);
        userGateway.updatePassword(encryptedNewPassword, currentUserId);
    }

}
