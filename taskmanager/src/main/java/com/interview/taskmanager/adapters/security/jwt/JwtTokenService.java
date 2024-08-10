package com.interview.taskmanager.adapters.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class JwtTokenService {

    private final SecretKey key;

    public JwtTokenService(@Value("${jwtTokenConfiguration.secretKey}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createJwt(Authentication authentication) {
        Map<String, Object> claims = generateClaims(authentication);
        Date issuedDate = new Date();
        Date expiredDate = Date.from(LocalDateTime
                .now()
                .plusMinutes(30)
                .atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .claims(claims)
                .subject(authentication.getPrincipal().toString())
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        return jwtParser(token).get("name", String.class);
    }

    private Claims jwtParser(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Map<String, Object> generateClaims(Authentication user) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roleList = user.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", roleList);
        return claims;
    }
}
