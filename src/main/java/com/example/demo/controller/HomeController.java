package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "CI/CD Demo Project");
        model.addAttribute("message", "Welcome to Spring Boot CI/CD Pipeline Demo!");
        model.addAttribute("status", "Application is running successfully");
        model.addAttribute("timestamp", System.currentTimeMillis());
        return "home";
    }
    
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About This Project");
        model.addAttribute("projectName", "Spring Boot CI/CD Demo");
        model.addAttribute("tools", new String[]{
            "Spring Boot", "Jenkins", "SonarQube", "Docker", "AWS EC2"
        });
        return "about";
    }
}
