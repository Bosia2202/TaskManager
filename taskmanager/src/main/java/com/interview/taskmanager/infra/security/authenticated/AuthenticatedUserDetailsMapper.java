package com.interview.taskmanager.infra.security.authenticated;

import com.interview.taskmanager.adapters.database.models.User;

public class AuthenticatedUserDetailsMapper {

    private AuthenticatedUserDetailsMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static AuthenticatedUserDetails toAuthenticatedUserDetails(User user) {
        AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails();
        authenticatedUserDetails.setUsername(user.getUsername());
        authenticatedUserDetails.setEmail(user.getEmail());
        authenticatedUserDetails.setPassword(user.getPassword());
        authenticatedUserDetails.setRole(user.getRole());
        return authenticatedUserDetails;
    }
}
