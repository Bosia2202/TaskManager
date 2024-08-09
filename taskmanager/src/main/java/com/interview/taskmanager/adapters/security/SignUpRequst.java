package com.interview.taskmanager.adapters.security;

import lombok.Data;

@Data
public class SignUpRequst {
    private String username;
    private String email;
    private String password;
}
