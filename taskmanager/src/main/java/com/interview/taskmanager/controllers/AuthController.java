package com.interview.taskmanager.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.interview.taskmanager.infra.security.authenticated.AuthenticationService;
import com.interview.taskmanager.infra.security.dto.SignInRequest;
import com.interview.taskmanager.infra.security.dto.SignUpRequest;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
@AllArgsConstructor
@Log4j2
public class AuthController {

    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registration(@RequestBody SignUpRequest signUpRequest) {
        authenticationService.registrationUser(signUpRequest);
        log.info(String.format("User [email = '%s', name = '%s'] has registered", signUpRequest.getEmail(),
                signUpRequest.getUsername()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> authentication(@RequestBody SignInRequest signInRequest) {
        try {
            String jwtToken = authenticationService.authenticationUser(signInRequest);
            log.info(String.format("User [email = '%s'] has authenticated", signInRequest.getEmail()));
            MultiValueMap<String, String> authenticationHeader = new HttpHeaders();
            authenticationHeader.add("Authorization", jwtToken);
            return new ResponseEntity<>(authenticationHeader, HttpStatus.OK);
        } catch (Exception e) {
            log.info(String.format("User [email = '%s'] wasn't authenticated. Error [message = %s]",
                    signInRequest.getEmail(), e.getMessage()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @ExceptionHandler(ExpiredJwtException.class)
    private String jwtExpired() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "redirect:/login";
    }
}
