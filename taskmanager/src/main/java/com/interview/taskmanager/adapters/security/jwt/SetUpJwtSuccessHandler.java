package com.interview.taskmanager.adapters.security.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SetUpJwtSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private JwtTokenService jwtTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String jwtToken = jwtTokenService.createJwt(authentication);
        response.setHeader("Authorization", "Bearer " + jwtToken);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
