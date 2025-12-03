package com.example.admin.util;

import com.example.admin.dto.LoginResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    
    public static String getAuthToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof LoginResponse) {
            LoginResponse loginResponse = (LoginResponse) authentication.getDetails();
            return loginResponse.getToken();
        }
        return null;
    }
    
    public static LoginResponse getLoginResponse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof LoginResponse) {
            return (LoginResponse) authentication.getDetails();
        }
        return null;
    }
}

