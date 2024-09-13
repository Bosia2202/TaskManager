package com.interview.taskmanager.adapters.out.postgresql.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.in.springsecurity.AuthenticateUser;
import com.interview.taskmanager.adapters.in.springsecurity.UserDetailsService;
import com.interview.taskmanager.adapters.out.postgresql.user.repository.UserRepository;
import com.interview.taskmanager.application.dto.DatabaseUserDto;
import com.interview.taskmanager.application.ports.out.UserPort;
import com.interview.taskmanager.domain.User;
import com.interview.taskmanager.infra.exception.UserNotFoundRuntimeException;

@Repository
public class PostgresUserAdapter implements UserPort, UserDetailsService {

    private final UserRepository userRepository;

    public PostgresUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthenticateUser loadUserByEmail(String email) {
        return userRepository.loadUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundRuntimeException("User with this email wasn't found"));
    }

    @Override
    public void create(User user, String role) {
        userRepository.create(user, role);
    }

    @Override
    public Optional<DatabaseUserDto> getUserById(Integer userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public List<DatabaseUserDto> getUsersByUsername(String username, Integer pageNumber, Integer pageSize) {
        return userRepository.getUsersByUsername(username, pageNumber, pageSize);
    }

    @Override
    public String getUsernameById(Integer userId) {
        return userRepository.getUsernameById(userId);
    }

    @Override
    public Optional<DatabaseUserDto> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public boolean remove(Integer userId) {
        return userRepository.remove(userId);
    }

}
