package com.example.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("page", "dashboard");
        return "dashboard";
    }

    @GetMapping("/merchants")
    public String merchants(Model model) {
        model.addAttribute("page", "merchants");
        return "merchants";
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
    public String users(Model model) {
        model.addAttribute("page", "users");
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


