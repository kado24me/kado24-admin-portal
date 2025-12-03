package com.example.admin.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Merchant {
    private UUID id;
    private String businessName;
    private String email;
    private String phoneNumber;
    private String status;
    private String address;
    private OffsetDateTime appliedAt;
    private OffsetDateTime reviewedAt;
    
    public Merchant() {}
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getBusinessName() {
        return businessName;
    }
    
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public OffsetDateTime getAppliedAt() {
        return appliedAt;
    }
    
    public void setAppliedAt(OffsetDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
    
    public OffsetDateTime getReviewedAt() {
        return reviewedAt;
    }
    
    public void setReviewedAt(OffsetDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}

