package com.interview.taskmanager.adapters.in.springsecurity.jwt;

import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtParser {

    private final SecretKeyGenerator secretKeyGenerator;

    public JwtParser(SecretKeyGenerator secretKeyGenerator) {
        this.secretKeyGenerator = secretKeyGenerator;
    }

    public String extractEmail(String token) {
        return jwtParser(token).get("email", String.class);
    }

    private Claims jwtParser(String token) {
        return Jwts.parser()
                .verifyWith(secretKeyGenerator.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = jwtParser(token);
        return claimResolver.apply(claims);
    }
}
