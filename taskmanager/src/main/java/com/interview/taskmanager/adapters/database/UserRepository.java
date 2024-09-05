package com.interview.taskmanager.adapters.database;

import java.util.List;

import com.interview.taskmanager.domain.user.BriefUserInfo;
import com.interview.taskmanager.domain.user.Role;

public interface UserRepository {

    void create(String email, String defaultAvatarUrl, String username, String password, Role role);

    void updateUsername(String newUsername, Integer userId);

    String getPassword(Integer userId);

    void updatePassword(String encryptedNewPassword, Integer userId);

    void remove(Integer userId);

    DatabaseUserDto getUserById(Integer userId);

    List<BriefUserInfo> getUsersByUsername(String username, Integer pageNumber, Integer pAGE_SIZE);

    String getAvatarUrl(Integer currentUserId);

    void updateAvatarUrl(String newAvatarUrl, Integer currentUserId);
}
