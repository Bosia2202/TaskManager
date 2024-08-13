package com.interview.taskmanager.infra.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.interview.taskmanager.infra.security.authenticated.AuthenticatedUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtTokenOperation implements JwtTokenService {

    private final SecretKey SECRET_KEY;
    private static final Integer EXPIRATION_TIME = 30;

    public JwtTokenOperation(@Value("${jwtTokenConfiguration.secretKey}") String secretKey) {
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

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return jwtParser(token).getSubject();
    }

    public String extractEmail(String token) {
        return jwtParser(token).get("email", String.class);
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
        AuthenticatedUserDetails user = (AuthenticatedUserDetails) authentication.getPrincipal();
        List<String> roleList = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String email = user.getEmail();
        claims.put("roles", roleList);
        claims.put("email", email);
        return claims;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = jwtParser(token);
        return claimResolver.apply(claims);
    }
}
