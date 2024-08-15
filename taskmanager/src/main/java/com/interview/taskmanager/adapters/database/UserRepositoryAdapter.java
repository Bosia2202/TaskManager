package com.interview.taskmanager.adapters.database;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.profile.UserProfile;
import com.interview.taskmanager.infra.security.authenticated.AuthenticatedUserDetails;

@Repository
public interface UserRepositoryAdapter {

    void createUser(User user);

    void updateUserUsername(Integer id, String newUsername);

    void updateUserEmail(Integer id, String newEmail);

    void updateUserPassword(Integer id, String newPassword);

    void deleteUserById(Integer id);

    void deleteUserByUsername(String name);

    AuthenticatedUserDetails getAuthorizationInfo(String email);

    User findById(Integer id);

    User findByEmail(String email);

    User findByUsername(String username);

    UserProfile findUserProfileById(Integer id);

    UserProfile findUserProfileByUsername(String username);

    User findUserWithAssignedTasksByUsername(String username);

}