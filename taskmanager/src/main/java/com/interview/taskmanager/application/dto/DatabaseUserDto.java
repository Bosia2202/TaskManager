package com.interview.taskmanager.application.dto;

public record DatabaseUserDto(Integer id, String email, String avatarUrl, String username,
        String password) {

}
