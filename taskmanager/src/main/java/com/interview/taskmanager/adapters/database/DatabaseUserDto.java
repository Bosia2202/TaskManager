package com.interview.taskmanager.adapters.database;

import com.interview.taskmanager.domain.user.Role;

public record DatabaseUserDto(Integer id, String email, String avatarUrl, String username,
                              String password, Role role) {

}
