package com.interview.taskmanager.adapters.security.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String email;
    private CharSequence password;

}
