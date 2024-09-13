package com.interview.taskmanager.adapters.in.springsecurity;

import java.nio.CharBuffer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.interview.taskmanager.adapters.in.springsecurity.jwt.JwtGenerator;
import com.interview.taskmanager.application.dto.SignIn;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.domain.User;

public class SpringSecurityAdapter implements SecurityPort {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtGenerator jwtGenerator;

    public SpringSecurityAdapter(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
            JwtGenerator jwtGenerator) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getUsername();
    }

    @Override
    public String encryptPassword(char[] password) {
        CharSequence passwordSequence = CharBuffer.wrap(password);
        return passwordEncoder.encode(passwordSequence);
    }

    @Override
    public boolean authentication(SignIn signIn) {
        EmailPasswordAuthenticationToken authenticationToken = new EmailPasswordAuthenticationToken(signIn.email(),
                signIn.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    @Override
    public String generateToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtGenerator.generate(authentication);
        return "Bearer " + jwtToken;
    }

}
