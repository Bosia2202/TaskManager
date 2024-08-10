package com.interview.taskmanager.adapters.security.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
