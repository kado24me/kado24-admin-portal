package com.example.admin.config;

import com.example.admin.dto.LoginResponse;
import com.example.admin.service.AuthService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private final AuthService authService;
    
    public CustomAuthenticationProvider(AuthService authService) {
        this.authService = authService;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        // Call your auth service
        LoginResponse loginResponse = authService.authenticate(username, password);
        
        if (loginResponse == null || loginResponse.getToken() == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        
        // Store token in authentication details for later use
        // Handle role - it comes as enum value (e.g., "ADMIN", "USER", "MERCHANT")
        String role = loginResponse.getRole() != null ? loginResponse.getRole() : "USER";
        // Remove "ROLE_" prefix if present, then add it back for Spring Security
        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
        );
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            username,
            password,
            authorities
        );
        
        // Store token and user info in details
        authToken.setDetails(loginResponse);
        
        return authToken;
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

