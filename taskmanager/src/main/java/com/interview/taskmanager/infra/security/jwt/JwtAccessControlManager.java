package com.interview.taskmanager.infra.security.jwt;

import java.util.function.Supplier;

import org.apache.tomcat.util.descriptor.web.SecurityRoleRef;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtAccessControlManager implements AuthorizationManager<RequestAuthorizationContext> {

  private JwtTokenService jwtTokenService;

  @Override
  public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier,
      RequestAuthorizationContext requestAuthorizationContext) {
    HttpServletRequest request = requestAuthorizationContext.getRequest();
    String header = request.getHeader("Authorization");
    if (!(header.isEmpty() && header.startsWith("Bearer "))) {
      String jwtToken = header.substring(7);
      String usernameFromJwt = jwtTokenService.fetchUsernameFromJwt(jwtToken);  
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (usernameFromJwt.equals(authenticationSupplier.get().getName())) {
        return new AuthorizationDecision(true);
      }
    }
    return new AuthorizationDecision(false);
  }
}
