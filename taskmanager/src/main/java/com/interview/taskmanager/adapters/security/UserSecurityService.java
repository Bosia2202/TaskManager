package com.interview.taskmanager.adapters.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.UserRepository;
import com.interview.taskmanager.adapters.security.dto.SignUpRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserSecurityService implements IUserSecurityService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }

    @Override
    public void registerUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setUsername(signUpRequest.getUsername());
        repository.createUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        repository.deleteUserById(id);
    }

}
