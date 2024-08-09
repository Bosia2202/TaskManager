package com.interview.taskmanager.domain.services.user;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.UserProfile;

public interface UserManagerService {
    void registerUser(User user);
    void loginUser(String username, Character[] password);
    UserProfile getUserProfile(Integer id);
    void updateUserProfile(Integer userId, UserProfile userProfile);
    void deleteUser(Integer userId);
    void resetPassword(String email);
}
