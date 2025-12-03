package com.example.admin.dto;

public class MerchantStatistics {
    private Long totalMerchants;
    private Long pendingMerchants;
    private Long approvedMerchants;
    private Long rejectedMerchants;
    private Long suspendedMerchants;
    
    public MerchantStatistics() {}
    
    public Long getTotalMerchants() {
        return totalMerchants;
    }
    
    public void setTotalMerchants(Long totalMerchants) {
        this.totalMerchants = totalMerchants;
    }
    
    public Long getPendingMerchants() {
        return pendingMerchants;
    }
    
    public void setPendingMerchants(Long pendingMerchants) {
        this.pendingMerchants = pendingMerchants;
    }
    
    public Long getApprovedMerchants() {
        return approvedMerchants;
    }
    
    public void setApprovedMerchants(Long approvedMerchants) {
        this.approvedMerchants = approvedMerchants;
    }
    
    public Long getRejectedMerchants() {
        return rejectedMerchants;
    }
    
    public void setRejectedMerchants(Long rejectedMerchants) {
        this.rejectedMerchants = rejectedMerchants;
    }
    
    public Long getSuspendedMerchants() {
        return suspendedMerchants;
    }
    
    public void setSuspendedMerchants(Long suspendedMerchants) {
        this.suspendedMerchants = suspendedMerchants;
    }
}

