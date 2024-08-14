package com.interview.taskmanager.infra.security.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.infra.security.Role;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String email;
    private CharSequence password;

    public User buildUserFromSignUpRequest(PasswordEncoder passwordEncoder) {
        User registeredUser = new User();
        registeredUser.setEmail(email);
        registeredUser.setUsername(username);
        registeredUser.setPassword(passwordEncoder.encode(password));
        registeredUser.setRole(Role.ROLE_DEFAULT_USER);
        return registeredUser;
    }
}
