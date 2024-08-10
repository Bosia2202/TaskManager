package com.interview.taskmanager.adapters.security;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.interview.taskmanager.adapters.security.dto.SignUpRequest;


public interface IUserSecurityService extends UserDetailsService {
    void registerUser(SignUpRequest signUpRequest);
    void deleteUser(Integer userId);
}
