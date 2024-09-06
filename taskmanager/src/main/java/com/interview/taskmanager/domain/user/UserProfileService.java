package com.interview.taskmanager.domain.user;

import com.interview.taskmanager.domain.exception.UserProfileNotFoundRuntimeException;

public class UserProfileService {

    private final UserGateway userGateway;

    public UserProfileService(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public ProfileDto getUserProfile(Integer userId) {
        return userGateway.getUserProfile(userId).orElseThrow(() -> buildUserProfileNotFoundRuntimeException(userId));
    }

    private UserProfileNotFoundRuntimeException buildUserProfileNotFoundRuntimeException(Integer userId) {
        String message = String.format("User profile with id = '%d' wasn't found", userId);
        return new UserProfileNotFoundRuntimeException(message);
    }
}
