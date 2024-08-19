package com.interview.taskmanager.infra.security.jwt;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

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

    private String secretKey = "";
    private static final Integer EXPIRATION_TIME_MINUTES = 30;

    public JwtTokenOperation() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey cipherKey = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(cipherKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            log.error("JWT algorithm want found.", e);
        }
    }

    public String generateJwt(Authentication authentication) {
        Map<String, Object> claims = generateClaims(authentication);
        Date issuedDate = new Date();
        Date expiryDate = Date.from(LocalDateTime.now()
                .plusMinutes(EXPIRATION_TIME_MINUTES)
                .atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .claims(claims)
                .issuedAt(issuedDate)
                .expiration(expiryDate)
                .signWith(getKey())
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
                .verifyWith(getKey())
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

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
