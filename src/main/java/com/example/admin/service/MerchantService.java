package com.example.admin.service;

import com.example.admin.dto.ApiResponse;
import com.example.admin.dto.Merchant;
import com.example.admin.dto.MerchantStatistics;
import com.example.admin.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class MerchantService {
    
    private final RestTemplate restTemplate;
    
    @Value("${merchant.service.url:http://localhost:8080/api/merchants}")
    private String merchantServiceUrl;
    
    public MerchantService(RestTemplate restTemplate) {
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
    
    public MerchantStatistics getStatistics() {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<MerchantStatistics>> responseType = 
                new ParameterizedTypeReference<ApiResponse<MerchantStatistics>>() {};
            
            ResponseEntity<ApiResponse<MerchantStatistics>> response = restTemplate.exchange(
                merchantServiceUrl + "/admin/statistics",
                HttpMethod.GET,
                httpEntity,
                responseType
            );
            
            ApiResponse<MerchantStatistics> apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null) {
                return apiResponse.getData();
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error fetching merchant statistics: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Merchant> getPendingMerchants() {
        return getPendingMerchants(0, 100);
    }
    
    public List<Merchant> getPendingMerchants(int page, int size) {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<com.example.admin.dto.PageResponse<Merchant>>> responseType = 
                new ParameterizedTypeReference<ApiResponse<com.example.admin.dto.PageResponse<Merchant>>>() {};
            
            String url = merchantServiceUrl + "/admin/pending?page=" + page + "&size=" + size;
            ResponseEntity<ApiResponse<com.example.admin.dto.PageResponse<Merchant>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                responseType
            );
            
            ApiResponse<com.example.admin.dto.PageResponse<Merchant>> apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null) {
                List<Merchant> content = apiResponse.getData().getContent();
                return content != null ? content : List.of();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public Merchant getMerchantDetails(UUID id) {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<Merchant>> responseType = 
                new ParameterizedTypeReference<ApiResponse<Merchant>>() {};
            
            ResponseEntity<ApiResponse<Merchant>> response = restTemplate.exchange(
                merchantServiceUrl + "/admin/" + id + "/details",
                HttpMethod.GET,
                httpEntity,
                responseType
            );
            
            ApiResponse<Merchant> apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null) {
                return apiResponse.getData();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Merchant> getMerchantsByStatus(String status) {
        return getMerchantsByStatus(status, 0, 100);
    }
    
    public List<Merchant> getMerchantsByStatus(String status, int page, int size) {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<com.example.admin.dto.PageResponse<Merchant>>> responseType = 
                new ParameterizedTypeReference<ApiResponse<com.example.admin.dto.PageResponse<Merchant>>>() {};
            
            String url = merchantServiceUrl + "/status/" + status + "?page=" + page + "&size=" + size;
            ResponseEntity<ApiResponse<com.example.admin.dto.PageResponse<Merchant>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                responseType
            );
            
            ApiResponse<com.example.admin.dto.PageResponse<Merchant>> apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null) {
                List<Merchant> content = apiResponse.getData().getContent();
                return content != null ? content : List.of();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public boolean approveMerchant(UUID id) {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<Merchant>> responseType = 
                new ParameterizedTypeReference<ApiResponse<Merchant>>() {};
            
            ResponseEntity<ApiResponse<Merchant>> response = restTemplate.exchange(
                merchantServiceUrl + "/" + id + "/approve",
                HttpMethod.POST,
                httpEntity,
                responseType
            );
            
            ApiResponse<Merchant> apiResponse = response.getBody();
            return apiResponse != null && apiResponse.isSuccess();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean rejectMerchant(UUID id) {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<Merchant>> responseType = 
                new ParameterizedTypeReference<ApiResponse<Merchant>>() {};
            
            ResponseEntity<ApiResponse<Merchant>> response = restTemplate.exchange(
                merchantServiceUrl + "/" + id + "/reject",
                HttpMethod.POST,
                httpEntity,
                responseType
            );
            
            ApiResponse<Merchant> apiResponse = response.getBody();
            return apiResponse != null && apiResponse.isSuccess();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean suspendMerchant(UUID id) {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<Merchant>> responseType = 
                new ParameterizedTypeReference<ApiResponse<Merchant>>() {};
            
            ResponseEntity<ApiResponse<Merchant>> response = restTemplate.exchange(
                merchantServiceUrl + "/" + id + "/suspend",
                HttpMethod.POST,
                httpEntity,
                responseType
            );
            
            ApiResponse<Merchant> apiResponse = response.getBody();
            return apiResponse != null && apiResponse.isSuccess();
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Merchant> getAllMerchants(int page, int size) {
        try {
            HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
            
            ParameterizedTypeReference<ApiResponse<com.example.admin.dto.PageResponse<Merchant>>> responseType = 
                new ParameterizedTypeReference<ApiResponse<com.example.admin.dto.PageResponse<Merchant>>>() {};
            
            String url = merchantServiceUrl + "?page=" + page + "&size=" + size;
            ResponseEntity<ApiResponse<com.example.admin.dto.PageResponse<Merchant>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                responseType
            );
            
            ApiResponse<com.example.admin.dto.PageResponse<Merchant>> apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null) {
                List<Merchant> content = apiResponse.getData().getContent();
                return content != null ? content : List.of();
            }
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}

