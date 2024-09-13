package com.interview.taskmanager.adapters.in.springsecurity.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.interview.taskmanager.adapters.in.springsecurity.AuthenticateUser;
import com.interview.taskmanager.adapters.in.springsecurity.EmailPasswordAuthenticationToken;
import com.interview.taskmanager.adapters.in.springsecurity.jwt.JwtParser;
import com.interview.taskmanager.adapters.in.springsecurity.jwt.JwtValidator;
import com.interview.taskmanager.adapters.out.postgresql.user.PostgresUserAdapter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    private final ApplicationContext context;

    private final JwtParser jwtParser;

    private final JwtValidator jwtValidator;

    public JwtFilter(ApplicationContext context, JwtParser jwtParser, JwtValidator jwtValidator) {
        this.context = context;
        this.jwtParser = jwtParser;
        this.jwtValidator = jwtValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String email = null;
        String token = null;
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            email = jwtParser.extractEmail(token);
        }
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            AuthenticateUser user = context.getBean(PostgresUserAdapter.class).loadUserByEmail(email);
            if (jwtValidator.validateToken(token, user)) {
                EmailPasswordAuthenticationToken authToken = new EmailPasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
