package com.interview.taskmanager.domain.user;

public record CreateUserDto(String email, String username, char[] password) {
}
