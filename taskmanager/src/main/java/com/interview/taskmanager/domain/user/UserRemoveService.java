package com.interview.taskmanager.domain.user;

import com.interview.taskmanager.domain.security.IdentificationUserService;

public class UserRemoveService {

    private final UserGateway userGateway;

    private final IdentificationUserService identificationUserService;

    public UserRemoveService(UserGateway userGateway, IdentificationUserService identificationUserService) {
        this.userGateway = userGateway;
        this.identificationUserService = identificationUserService;
    }

    public void remove() {
        Integer currentUserId = identificationUserService.getCurrentUserId();
        userGateway.remove(currentUserId);
    }

}
