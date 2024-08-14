package com.interview.taskmanager.infra.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    String generateJwt(Authentication authentication);

    public boolean validateToken(String token, UserDetails userDetails);

    public String extractUsername(String token);

    public String extractEmail(String token);
    
}
