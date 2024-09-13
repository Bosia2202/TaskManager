package com.interview.taskmanager.adapters.in.springsecurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;

    private UserDetailsService userDetailsService;

    public EmailPasswordAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        AuthenticateUser user = userDetailsService.loadUserByEmail(email);
        if (isValidPassword(email, password)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.addAll(user.getAuthorities());
            return new EmailPasswordAuthenticationToken(email, password, authorities);
        } else {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    private boolean isValidPassword(String validPassword, String password) {
        String encryptedPassword = passwordEncoder.encode(password);
        return encryptedPassword.equals(validPassword);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailPasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
