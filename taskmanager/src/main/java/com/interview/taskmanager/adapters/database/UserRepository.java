package com.interview.taskmanager.adapters.database;

import java.util.List;
import java.util.Optional;

import com.interview.taskmanager.domain.user.Role;

public interface UserRepository {

    void create(String email, String defaultAvatarUrl, String username, String password, Role role);

    Optional<DatabaseUserDto> getUserById(Integer userId);

    String getAvatarUrl(Integer currentUserId);

    List<DatabaseUserDto> getUsersByUsername(String username, Integer pageNumber, Integer pAGE_SIZE);

    String getPassword(Integer userId);

    void updateAvatarUrl(String newAvatarUrl, Integer currentUserId);

    void updateUsername(String newUsername, Integer userId);

    void updatePassword(String encryptedNewPassword, Integer userId);

    void remove(Integer userId);

}
