package com.interview.taskmanager.application.usecase.account;

import com.interview.taskmanager.application.dto.DatabaseUserDto;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.application.ports.out.AvatarPort;
import com.interview.taskmanager.application.ports.out.UserPort;
import com.interview.taskmanager.domain.User;
import com.interview.taskmanager.infra.exception.IncorrectPasswordRuntimeException;
import com.interview.taskmanager.infra.exception.UserNotFoundRuntimeException;

public class UpdateAccountService {

    private final UserPort userPort;

    private final AvatarPort avatarPort;

    private final SecurityPort securityPort;

    public UpdateAccountService(UserPort userPort, AvatarPort avatarPort, SecurityPort securityPort) {
        this.userPort = userPort;
        this.avatarPort = avatarPort;
        this.securityPort = securityPort;
    }

    public void updateAvatar(byte[] image) {
        Integer currentUserId = securityPort.getCurrentUserId();
        DatabaseUserDto databaseUser = getUserFromDatabaseById(currentUserId);
        String oldAvatarUrl = databaseUser.avatarUrl();
        if (!oldAvatarUrl.equals(avatarPort.getDefaultAvatarImgUrl())) {
            avatarPort.deleteAvatarImg(oldAvatarUrl);
        }
        User user = new User(databaseUser.id(), databaseUser.email(), avatarPort.uploadAvatarImg(image),
                databaseUser.username(), databaseUser.password());
        userPort.updateUser(user);
    }

    private DatabaseUserDto getUserFromDatabaseById(Integer id) {
        return userPort.getUserById(id)
                .orElseThrow(() -> new UserNotFoundRuntimeException(String.format("User with id = '%d' wasn't found.", id)));
    }

    public void updateUsername(String newUsername) {
        Integer currentUserId = securityPort.getCurrentUserId();
        DatabaseUserDto databaseUser = getUserFromDatabaseById(currentUserId);
        User user = new User(databaseUser.id(), databaseUser.email(), databaseUser.avatarUrl(), newUsername,
                databaseUser.password());
        userPort.updateUser(user);
    }

    public void updatePassword(char[] oldPassword, char[] newPassword) {
        Integer currentUserId = securityPort.getCurrentUserId();
        DatabaseUserDto databaseUser = getUserFromDatabaseById(currentUserId);
        String encryptedOldPassword = securityPort.encryptPassword(oldPassword);
        if (!encryptedOldPassword.equals(databaseUser.password())) {
            throw new IncorrectPasswordRuntimeException("Wrong old password");
        }
        String updatedPassword = securityPort.encryptPassword(newPassword);
        User user = new User(currentUserId, databaseUser.email(), databaseUser.avatarUrl(), databaseUser.username(),
                updatedPassword);
        userPort.updateUser(user);
    }

}
