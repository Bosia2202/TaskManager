package com.interview.taskmanager.domain.user;

import java.util.List;

public interface UserGateway {

    void create(String email, String defaultAvatarUrl, String username, String password, Role role);

    void updateUsername(String newUsername, Integer userId);

    String getPassword(Integer userId);

    void updatePassword(String encryptedNewPassword, Integer userId);

    void remove(Integer userId);

    ProfileDto getUserProfile();

    List<BriefUserInfo> getUsersByUsername(String username, Integer pageNumber, Integer pageSize);

    String getUserAvatarUrl(Integer currentUserId);

    void updateUserAvatarUrl(String newAvatarUrl, Integer currentUserId);
}
