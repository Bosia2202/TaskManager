package com.interview.taskmanager.adapters.in.springsecurity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface AuthenticateUser {

    String getEmail();

    String getPassword();

    Collection<? extends GrantedAuthority> getAuthorities();
}
