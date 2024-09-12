package com.interview.taskmanager.adapters.in.springsecurity;

import java.nio.CharBuffer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.interview.taskmanager.application.dto.DatabaseUserDto;
import com.interview.taskmanager.application.ports.in.SecurityPort;
import com.interview.taskmanager.domain.User;

public class SpringSecurityAdapter implements SecurityPort {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public SpringSecurityAdapter(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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
    public boolean authentication(DatabaseUserDto databaseUser) {
        EmailPasswordAuthenticationToken authenticationToken = new EmailPasswordAuthenticationToken(
                databaseUser.email(), databaseUser.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true; //TODO: Можно убрать boolean
    }

    @Override
    public String generateToken() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateToken'");
    }

}
