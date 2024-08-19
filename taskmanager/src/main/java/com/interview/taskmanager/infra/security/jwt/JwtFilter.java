package com.interview.taskmanager.infra.security.jwt;

import java.io.IOException;


import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.interview.taskmanager.infra.security.UserDetailsProvider;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@AllArgsConstructor
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    ApplicationContext context;

    JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String email = null;
        String token = null;
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            email = extractEmailFromToken(token);
        }
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(UserDetailsProvider.class).loadUserByUsername(email);
            if (jwtTokenService.validateToken(token, userDetails)) {
                authenticateUserWithRequestDetails(userDetails, request);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractEmailFromToken(String token) throws AccessDeniedException {
        try {
            return jwtTokenService.extractEmail(token);
        } catch (JwtException exception) {
            log.error(exception.getMessage());
            throw new AccessDeniedException("Access denied");
        }
    }

    private void authenticateUserWithRequestDetails(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource()
                .buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

}
