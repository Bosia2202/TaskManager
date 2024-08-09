package com.interview.taskmanager.adapters.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenUtil implements CsrfTokenRepository {
    @Value("${jwtTokenConfiguration.secretKey}")
    private String secret;

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        String token = "";
        try {
            token = Jwts.builder()
            .id(id)
            .issuedAt(now)
            .signWith(secretKey)
            .expiration(exp)
            .compact();
        } catch (JwtException exception) {
            exception.printStackTrace();
        }
        return new DefaultCsrfToken("x-csrf-token", "_csrf", token);
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'loadToken'");
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Unimplemented method 'saveToken'");
    }

}
