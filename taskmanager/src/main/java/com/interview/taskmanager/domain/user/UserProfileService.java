package com.interview.taskmanager.domain.user;

public class UserProfileService {

    private final UserGateway userGateway;

    public UserProfileService(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public ProfileDto getUserProfile(Integer userId) {
        return userGateway.getUserProfile();
    }
}
