package com.example.admin.service;

import com.example.admin.dto.ApiResponse;
import com.example.admin.dto.LoginRequest;
import com.example.admin.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
    
    private final RestTemplate restTemplate;
    
    @Value("${auth.service.url:http://localhost:8080/api/auth}")
    private String authServiceUrl;
    
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public LoginResponse authenticate(String username, String password) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            LoginRequest request = new LoginRequest(username, password);
            HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(request, headers);
            
            ParameterizedTypeReference<ApiResponse<LoginResponse>> responseType = 
                new ParameterizedTypeReference<ApiResponse<LoginResponse>>() {};
            
            ResponseEntity<ApiResponse<LoginResponse>> response = restTemplate.exchange(
                authServiceUrl + "/login",
                HttpMethod.POST,
                httpEntity,
                responseType
            );
            
            ApiResponse<LoginResponse> apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null) {
                return apiResponse.getData();
            }
            
            return null;
        } catch (HttpClientErrorException e) {
            // Authentication failed - invalid credentials
            return null;
        } catch (Exception e) {
            // Other errors (network, etc.)
            return null;
        }
    }
}

