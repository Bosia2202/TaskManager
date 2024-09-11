package com.interview.taskmanager.application.ports.out;

import java.util.List;
import java.util.Optional;

import com.interview.taskmanager.application.dto.DatabaseUserDto;
import com.interview.taskmanager.domain.User;

public interface UserPort {

    void updateUsername(String newUsername, Integer userId);

    String getPassword(Integer userId);

    void updatePassword(String encryptedNewPassword, Integer userId);

    boolean remove(Integer userId);

    Optional<DatabaseUserDto> getUserById(Integer userId);

    List<DatabaseUserDto> getUsersByUsername(String username, Integer pageNumber);

    String getUserAvatarUrl(Integer currentUserId);

    void updateUserAvatarUrl(String newAvatarUrl, Integer currentUserId);

    boolean create(User user);

    Optional<User> findByEmail(String email);

    void updateUser(User user);

    String getUsernameById(Integer authorId);
}
