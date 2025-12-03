package com.example.admin.service;

import com.example.admin.dto.ApiResponse;
import com.example.admin.dto.User;
import com.example.admin.dto.UserStatistics;
import com.example.admin.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    
    private final RestTemplate restTemplate;
    
    @Value("${user.service.url:http://localhost:8080/api/users}")
    private String userServiceUrl;
    
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = SecurityUtils.getAuthToken();
        if (token != null) {
            headers.setBearerAuth(token);
        }
        return headers;
    }
    
    public UserStatistics getStatistics() {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<UserStatistics>> responseType = 
                new ParameterizedTypeReference<ApiResponse<UserStatistics>>() {};
            
            ResponseEntity<ApiResponse<UserStatistics>> response = restTemplate.exchange(
                userServiceUrl + "/admin/statistics",
                HttpMethod.GET,
                httpEntity,
                responseType
            );
            
            ApiResponse<UserStatistics> apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null) {
                return apiResponse.getData();
            }
            return null;
        } catch (Exception e) {
            // If API doesn't exist yet, return null
            return null;
        }
    }
    
    public List<User> getAllUsers() {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<List<User>>> responseType = 
                new ParameterizedTypeReference<ApiResponse<List<User>>>() {};
            
            ResponseEntity<ApiResponse<List<User>>> response = restTemplate.exchange(
                userServiceUrl + "/admin/all",
                HttpMethod.GET,
                httpEntity,
                responseType
            );
            
            ApiResponse<List<User>> apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null) {
                return apiResponse.getData();
            }
            return List.of();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    public List<User> getUsersByStatus(String status) {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<List<User>>> responseType = 
                new ParameterizedTypeReference<ApiResponse<List<User>>>() {};
            
            ResponseEntity<ApiResponse<List<User>>> response = restTemplate.exchange(
                userServiceUrl + "/admin/status/" + status,
                HttpMethod.GET,
                httpEntity,
                responseType
            );
            
            ApiResponse<List<User>> apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null) {
                return apiResponse.getData();
            }
            return List.of();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    public List<User> getUsersByRole(String role) {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<List<User>>> responseType = 
                new ParameterizedTypeReference<ApiResponse<List<User>>>() {};
            
            ResponseEntity<ApiResponse<List<User>>> response = restTemplate.exchange(
                userServiceUrl + "/admin/role/" + role,
                HttpMethod.GET,
                httpEntity,
                responseType
            );
            
            ApiResponse<List<User>> apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null) {
                return apiResponse.getData();
            }
            return List.of();
        } catch (Exception e) {
            // If endpoint doesn't exist, filter from all users
            try {
                List<User> allUsers = getAllUsers();
                return allUsers.stream()
                    .filter(user -> role.equalsIgnoreCase(user.getRole()))
                    .collect(java.util.stream.Collectors.toList());
            } catch (Exception ex) {
                return List.of();
            }
        }
    }
}

