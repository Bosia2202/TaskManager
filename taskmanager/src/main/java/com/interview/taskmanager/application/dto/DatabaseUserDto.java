package com.interview.taskmanager.application.dto;

import com.interview.taskmanager.adapters.database.in.springsecurity.Role;

public record DatabaseUserDto(Integer id, String email, String avatarUrl, String username,
                              String password, Role role) {

}
