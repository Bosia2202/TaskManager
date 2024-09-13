package com.interview.taskmanager.adapters.in.springsecurity;

public interface UserDetailsService {

    AuthenticateUser loadUserByEmail(String email);

}
