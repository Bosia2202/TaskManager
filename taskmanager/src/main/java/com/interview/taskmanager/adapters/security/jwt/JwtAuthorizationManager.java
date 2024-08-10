package com.interview.taskmanager.adapters.security.jwt;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import jakarta.servlet.http.HttpServletRequest;

public class JwtAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

  @Autowired
  private JwtTokenService jwtTokenService;

  @Override
  public AuthorizationDecision check(Supplier<Authentication> autSupplier,
      RequestAuthorizationContext requestAuthorizationContext) {
    HttpServletRequest request = requestAuthorizationContext.getRequest();
    String header = request.getHeader("Authorization");
    if (!(header.isEmpty() && header.startsWith("Bearer "))) {
      String jwt = header.substring(7);
      String usernameJwt = jwtTokenService.getUsernameFromJwt(jwt);
      if (usernameJwt.equals(autSupplier.get().getPrincipal().toString())) {
        return new AuthorizationDecision(true);
      }
    }
    return new AuthorizationDecision(false);
  }
}
