package com.interview.taskmanager.application.ports.out;

import java.util.List;
import java.util.Optional;

import com.interview.taskmanager.application.dto.DatabaseUserDto;
import com.interview.taskmanager.domain.User;

public interface UserPort {

    void create(User user, String role);

    Optional<DatabaseUserDto> getUserById(Integer userId);

    List<DatabaseUserDto> getUsersByUsername(String username, Integer pageNumber, Integer pAGE_SIZE);

    String getUsernameById(Integer authorId);

    Optional<DatabaseUserDto> findByEmail(String email);

    void update(User user);

    boolean remove(Integer userId);

}
