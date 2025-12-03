package com.example.admin.dto;

public class UserStatistics {
    private Long totalUsers;
    private Long activeUsers;
    private Long blockedUsers;
    
    public UserStatistics() {}
    
    public Long getTotalUsers() {
        return totalUsers;
    }
    
    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }
    
    public Long getActiveUsers() {
        return activeUsers;
    }
    
    public void setActiveUsers(Long activeUsers) {
        this.activeUsers = activeUsers;
    }
    
    public Long getBlockedUsers() {
        return blockedUsers;
    }
    
    public void setBlockedUsers(Long blockedUsers) {
        this.blockedUsers = blockedUsers;
    }
}

