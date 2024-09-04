package com.interview.taskmanager.domain.taskmanager.user;

public record CreateUserDto(String email, String username, Character[] password) {
}
