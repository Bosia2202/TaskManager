package com.interview.taskmanager.infra.security.jwt;

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
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtTokenManager implements JwtTokenService {

    private final SecretKey SECRET_KEY;
    private static final Integer EXPIRATION_TIME = 30;

    public JwtTokenManager(@Value("${jwtTokenConfiguration.secretKey}") String secretKey) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generateJwt(Authentication authentication) {
        Map<String, Object> claims = generateClaims(authentication);
        Date issuedDate = new Date();
        Date expiryDate = Date.from(LocalDateTime.now()
                .plusMinutes(EXPIRATION_TIME)
                .atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .claims(claims)
                .issuedAt(issuedDate)
                .expiration(expiryDate)
                .signWith(SECRET_KEY)
                .subject(authentication.getName())
                .compact();
    }

    public String fetchUsernameFromJwt(String token) {
        return jwtParser(token).getSubject();
    }

    private Claims jwtParser(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Map<String, Object> generateClaims(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roleList = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();      
        claims.put("roles", roleList);
        return claims;
    }
}
