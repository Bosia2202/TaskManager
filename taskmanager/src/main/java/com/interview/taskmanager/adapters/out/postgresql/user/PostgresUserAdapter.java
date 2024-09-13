package com.interview.taskmanager.adapters.out.postgresql.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.interview.taskmanager.adapters.in.springsecurity.AuthenticateUser;
import com.interview.taskmanager.adapters.in.springsecurity.UserDetailsService;
import com.interview.taskmanager.adapters.out.postgresql.user.repository.UserRepository;
import com.interview.taskmanager.application.dto.DatabaseUserDto;
import com.interview.taskmanager.application.ports.out.UserPort;
import com.interview.taskmanager.domain.User;

@Repository
public class PostgresUserAdapter implements UserPort, UserDetailsService {

    private final UserRepository userRepository;

    public PostgresUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean create(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Optional<DatabaseUserDto> getUserById(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }

    @Override
    public List<DatabaseUserDto> getUsersByUsername(String username, Integer pageNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsersByUsername'");
    }

    @Override
    public String getUsernameById(Integer authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsernameById'");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }

    @Override
    public void updateUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public boolean remove(Integer userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public AuthenticateUser loadUserByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByEmail'");
    }

    @Override
    public boolean create(User user, String role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public DatabaseUserDto getUserByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserByEmail'");
    }

}
