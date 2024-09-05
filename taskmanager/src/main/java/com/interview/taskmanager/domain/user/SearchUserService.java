package com.interview.taskmanager.domain.user;

import java.util.List;

public class SearchUserService {

    private final UserGateway userGateway;

    public SearchUserService(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<BriefUserInfo> getUsersByUsername(String username, Integer pageNumber) {
        
        return userGateway.getUsersByUsername(username, pageNumber);
    }

}
