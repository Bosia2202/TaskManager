package com.interview.taskmanager.adapters.database.repositories;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.UserProfile;

public interface UserRepository {

    void createUser(User user);

    void updateUserUsername(Integer id, String newUsername);

    void updateUserEmail(Integer id, String newEmail);

    void updateUserPassword(Integer id, String newPassword);

    void deleteUserById(Integer id);

    void deleteUserByUsername(String name);

    UserProfile getUserProfile(Integer id);

    User getAuthorizationInfo(String email);

    User findById(Integer id);

    User findByEmail(String email);

    User findByIdWithTasks(Integer id);

    User findByUsernameWithTask(String username);

    User findByIdWithComments(Integer id);

    User findByUsernameWithComments(String username);

    User findByUsername(String username);

}
