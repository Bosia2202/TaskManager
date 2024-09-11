package com.interview.taskmanager.application.dto;

public record NewUserDto(String email, String username, char[] password) {
}
