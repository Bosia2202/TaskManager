package com.interview.taskmanager.application.ports.out;

import java.util.List;
import java.util.Optional;

import com.interview.taskmanager.application.dto.DatabaseUserDto;
import com.interview.taskmanager.domain.User;

public interface UserPort {

    boolean create(User user, String role);

    Optional<DatabaseUserDto> getUserById(Integer userId);

    List<DatabaseUserDto> getUsersByUsername(String username, Integer pageNumber);

    String getUsernameById(Integer authorId);

    Optional<User> findByEmail(String email);

    void updateUser(User user);

    boolean remove(Integer userId);

    DatabaseUserDto getUserByEmail(String email);
}
