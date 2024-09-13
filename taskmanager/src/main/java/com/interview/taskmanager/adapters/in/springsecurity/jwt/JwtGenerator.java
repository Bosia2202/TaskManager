package com.interview.taskmanager.adapters.in.springsecurity.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Jwts;

public class JwtGenerator {

    private final SecretKeyGenerator secretKeyGenerator;

    private static final Integer EXPIRATION_TIME_MINUTES = 30;

    public JwtGenerator(SecretKeyGenerator secretKeyGenerator) {
        this.secretKeyGenerator = secretKeyGenerator;
    }

    public String generate(Authentication authentication) {
        Map<String, Object> claims = generateClaims(authentication);
        Date issuedDate = new Date();
        Date expiryDate = Date.from(LocalDateTime.now()
                .plusMinutes(EXPIRATION_TIME_MINUTES)
                .atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .claims(claims)
                .issuedAt(issuedDate)
                .expiration(expiryDate)
                .signWith(secretKeyGenerator.getKey())
                .subject(authentication.getName())
                .compact();
    }

    private Map<String, Object> generateClaims(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roleList = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", roleList);
        return claims;
    }
}
