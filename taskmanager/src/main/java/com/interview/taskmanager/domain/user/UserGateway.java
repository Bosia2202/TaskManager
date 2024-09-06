package com.interview.taskmanager.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserGateway {

    boolean create(String email, String defaultAvatarUrl, String username, String password, Role role);

    void updateUsername(String newUsername, Integer userId);

    String getPassword(Integer userId);

    void updatePassword(String encryptedNewPassword, Integer userId);

    boolean remove(Integer userId);

    Optional<ProfileDto> getUserProfile(Integer userId);

    List<BriefUserInfo> getUsersByUsername(String username, Integer pageNumber);

    String getUserAvatarUrl(Integer currentUserId);

    void updateUserAvatarUrl(String newAvatarUrl, Integer currentUserId);
}
