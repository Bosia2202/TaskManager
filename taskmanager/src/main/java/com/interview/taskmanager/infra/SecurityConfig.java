package com.interview.taskmanager.infra;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer.SessionFixationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.interview.taskmanager.adapters.security.IUserSecurityService;
import com.interview.taskmanager.adapters.security.jwt.JwtAuthorizationManager;
import com.interview.taskmanager.adapters.security.jwt.SetUpJwtSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final IUserSecurityService userSecurityService;

    public SecurityConfig(IUserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/login")
                                .permitAll()
                                .requestMatchers("/api/**")
                                .access(jwtAuthManager())
                                .anyRequest()
                                .authenticated())
                .sessionManagement(session -> {
                    session.maximumSessions(1);
                    session.sessionFixation(SessionFixationConfigurer::newSession);
                });
        return http.build();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(passwordEncoder);
        authenticationProvider.setUserDetailsService(userSecurityService);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(true);
        return providerManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
    return new SetUpJwtSuccessHandler();
}

    private AuthorizationManager<RequestAuthorizationContext> jwtAuthManager() {
        return new JwtAuthorizationManager();
    }

}
