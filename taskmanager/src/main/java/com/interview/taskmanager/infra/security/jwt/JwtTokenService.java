package com.interview.taskmanager.infra.security.jwt;

import org.springframework.security.core.Authentication;

public interface JwtTokenService {

    String generateJwt(Authentication authentication);

    public String fetchUsernameFromJwt(String token);
}
