package com.example.admin.controller;

import com.example.admin.dto.Merchant;
import com.example.admin.dto.MerchantStatistics;
import com.example.admin.dto.User;
import com.example.admin.dto.UserStatistics;
import com.example.admin.service.MerchantService;
import com.example.admin.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {

    private final MerchantService merchantService;
    private final UserService userService;

    public AdminController(MerchantService merchantService, UserService userService) {
        this.merchantService = merchantService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        MerchantStatistics merchantStats = merchantService.getStatistics();
        UserStatistics userStats = userService.getStatistics();
        
        // Create a combined statistics object
        DashboardStatistics stats = new DashboardStatistics();
        if (merchantStats != null) {
            stats.totalMerchants = merchantStats.getTotalMerchants();
            stats.pendingMerchants = merchantStats.getPendingMerchants();
            stats.approvedMerchants = merchantStats.getApprovedMerchants();
            stats.rejectedMerchants = merchantStats.getRejectedMerchants();
            stats.suspendedMerchants = merchantStats.getSuspendedMerchants();
        }
        if (userStats != null) {
            stats.totalUsers = userStats.getTotalUsers();
            stats.activeUsers = userStats.getActiveUsers();
            stats.blockedUsers = userStats.getBlockedUsers();
        }
        
        model.addAttribute("page", "dashboard");
        model.addAttribute("statistics", stats);
        return "dashboard";
    }
    
    // Inner class to hold combined statistics
    public static class DashboardStatistics {
        public Long totalUsers;
        public Long activeUsers;
        public Long blockedUsers;
        public Long totalMerchants;
        public Long pendingMerchants;
        public Long approvedMerchants;
        public Long rejectedMerchants;
        public Long suspendedMerchants;
    }

    @GetMapping("/merchants")
    public String merchants(@RequestParam(required = false) String status,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "20") int size,
                           Model model) {
        List<Merchant> merchants = List.of();
        String currentStatus = status != null ? status : "PENDING";
        int pendingCount = 0;
        
        try {
            if (status == null || "ALL".equalsIgnoreCase(status)) {
                // Get all merchants using the main endpoint
                merchants = merchantService.getAllMerchants(page, size);
                currentStatus = "ALL";
            } else if ("PENDING".equalsIgnoreCase(status)) {
                merchants = merchantService.getPendingMerchants(page, size);
                pendingCount = merchants.size();
            } else {
                merchants = merchantService.getMerchantsByStatus(status, page, size);
            }
            
            // Get pending count only if not already fetched
            if (!"PENDING".equalsIgnoreCase(currentStatus)) {
                List<Merchant> pending = merchantService.getPendingMerchants(0, 10);
                pendingCount = pending != null ? pending.size() : 0;
            }
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load merchants: " + e.getMessage());
        }
        
        model.addAttribute("page", "merchants");
        model.addAttribute("merchants", merchants != null ? merchants : List.of());
        model.addAttribute("currentStatus", currentStatus);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("pendingCount", pendingCount);
        return "merchants";
    }

    @GetMapping("/merchants/{id}/details")
    public String merchantDetails(@PathVariable UUID id, 
                                  @RequestParam(required = false) String status,
                                  Model model) {
        Merchant merchant = merchantService.getMerchantDetails(id);
        if (merchant == null) {
            return "redirect:/merchants?error=notfound" + (status != null ? "&status=" + status : "");
        }
        model.addAttribute("page", "merchants");
        model.addAttribute("merchant", merchant);
        model.addAttribute("returnStatus", status);
        return "merchant-details";
    }

    @PostMapping("/merchants/{id}/approve")
    public String approveMerchant(@PathVariable UUID id, 
                                   @RequestParam(required = false, defaultValue = "PENDING") String status,
                                   RedirectAttributes redirectAttributes) {
        boolean success = merchantService.approveMerchant(id);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Merchant approved successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to approve merchant");
        }
        return "redirect:/merchants?status=" + status;
    }

    @PostMapping("/merchants/{id}/reject")
    public String rejectMerchant(@PathVariable UUID id,
                                 @RequestParam(required = false, defaultValue = "PENDING") String status,
                                 RedirectAttributes redirectAttributes) {
        boolean success = merchantService.rejectMerchant(id);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Merchant rejected successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to reject merchant");
        }
        return "redirect:/merchants?status=" + status;
    }

    @PostMapping("/merchants/{id}/suspend")
    public String suspendMerchant(@PathVariable UUID id,
                                  @RequestParam(required = false, defaultValue = "APPROVED") String status,
                                  RedirectAttributes redirectAttributes) {
        boolean success = merchantService.suspendMerchant(id);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Merchant suspended successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to suspend merchant");
        }
        return "redirect:/merchants?status=" + status;
    }

    @GetMapping("/transactions")
    public String transactions(Model model) {
        model.addAttribute("page", "transactions");
        return "transactions";
    }

    @GetMapping("/fraud")
    public String fraud(Model model) {
        model.addAttribute("page", "fraud");
        return "fraud";
    }

    @GetMapping("/users")
    public String users(@RequestParam(required = false) String status,
                       @RequestParam(required = false) String role,
                       Model model) {
        List<com.example.admin.dto.User> users = List.of();
        String currentFilter = "ALL";
        
        try {
            if (role != null && !role.isEmpty()) {
                // Filter by role (e.g., MERCHANT)
                users = userService.getUsersByRole(role);
                currentFilter = "ROLE_" + role;
            } else if (status != null && !status.isEmpty() && !"ALL".equalsIgnoreCase(status)) {
                // Filter by status
                users = userService.getUsersByStatus(status);
                currentFilter = status;
            } else {
                // Get all users
                users = userService.getAllUsers();
                currentFilter = "ALL";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load users: " + e.getMessage());
        }
        
        UserStatistics userStats = userService.getStatistics();
        
        model.addAttribute("page", "users");
        model.addAttribute("users", users != null ? users : List.of());
        model.addAttribute("currentStatus", status != null ? status : "ALL");
        model.addAttribute("currentRole", role);
        model.addAttribute("currentFilter", currentFilter);
        model.addAttribute("statistics", userStats);
        return "users";
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("page", "reports");
        return "reports";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}


