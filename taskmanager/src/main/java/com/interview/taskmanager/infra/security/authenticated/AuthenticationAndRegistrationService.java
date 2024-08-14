package com.interview.taskmanager.infra.security.authenticated;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.interview.taskmanager.adapters.database.models.User;
import com.interview.taskmanager.adapters.database.repositories.UserRepository;
import com.interview.taskmanager.infra.security.dto.SignInRequest;
import com.interview.taskmanager.infra.security.dto.SignUpRequest;
import com.interview.taskmanager.infra.security.jwt.JwtTokenService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class AuthenticationAndRegistrationService implements AuthenticationService {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private JwtTokenService jwtTokenService;

    @Override
    public void registrationUser(SignUpRequest signUpRequest) {
        User registeredUser = signUpRequest.buildUserFromSignUpRequest(passwordEncoder);
        userRepository.createUser(registeredUser);
    }

    @Override
    public String authenticationUser(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(), signInRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken); 
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return generateJwtToken(authentication);
    }

    private String generateJwtToken(Authentication authentication) {
        String jwtToken = jwtTokenService.generateJwt(authentication);
        return "Bearer " + jwtToken;
    }

}
