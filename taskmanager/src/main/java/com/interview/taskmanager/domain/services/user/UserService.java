package com.interview.taskmanager.domain.services.user;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.common.dto.UserProfile;

public class UserService implements UserManagerService {

    @Override
    public void registerUser(User user) {
        
    }

    @Override
    public void loginUser(String username, Character[] password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loginUser'");
    }

    @Override
    public UserProfile getUserProfile(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserProfile'");
    }

    @Override
    public void updateUserProfile(Integer userId, UserProfile userProfile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserProfile'");
    }

    @Override
    public void deleteUser(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public void resetPassword(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetPassword'");
    }

}
