package com.interview.taskmanager.adapters.database.repositories;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.UserProfile;

public interface UserRepository {
    User findById(int id);

    User findByEmail(String email);
  
    User findByIdWithTasks(int id);

    User findByUsernameWithTask(String username);

    User findByIdWithComments(int id);

    User findByUsernameWithComments(String username);

    User findByUsername(String username);

    void deleteUserById(int id);

    void deleteUserByUsername(String name);

    void updateUserUsername(int id, String newUsername);

    void updateUserEmail(int id, String newEmail);

    void updateUserPassword(int id, String newPassword);

    void createUser(User user);

    UserProfile getUserProfile(Integer id);

}
