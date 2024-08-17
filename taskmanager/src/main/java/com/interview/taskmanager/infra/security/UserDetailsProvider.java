package com.interview.taskmanager.infra.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.interview.taskmanager.adapters.database.UserRepositoryAdapter;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserDetailsProvider implements UserDetailsService {

    private UserRepositoryAdapter userRepositoryAdapter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepositoryAdapter.getUserAuthorizationInfo(email);
    }

}
