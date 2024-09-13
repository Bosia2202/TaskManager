package com.interview.taskmanager.adapters.in.springsecurity.jwt;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SecretKeyGenerator {

    private String secretKey = "";

    public SecretKeyGenerator() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey cipherKey = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(cipherKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            log.error("JWT algorithm want found.", e);
        }
    }

    public SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
