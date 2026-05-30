package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {
    
    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Application is running successfully!");
        response.put("timestamp", java.time.Instant.now().toString());
        return response;
    }
    
    @GetMapping("/")
    public String home() {
        return "Hello from Spring Boot! Your app is working!";
    }
}