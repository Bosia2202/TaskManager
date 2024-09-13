package com.interview.taskmanager.adapters.in.springsecurity.jwt;

import java.util.Date;

import com.interview.taskmanager.adapters.in.springsecurity.AuthenticateUser;

public class JwtValidator {

    private final JwtParser jwtParser;

    public JwtValidator(JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    public boolean validateToken(String token, AuthenticateUser authUser) {
        final String email = jwtParser.extractEmail(token);
        return (email.equals(authUser.getEmail()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return jwtParser.extractExpiration(token).before(new Date());
    }

}
