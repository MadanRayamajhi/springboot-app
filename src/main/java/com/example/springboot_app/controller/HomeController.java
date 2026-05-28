package com.example.springboot_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
            "message", "Welcome to Spring Boot App!",
            "timestamp", LocalDateTime.now().toString(),
            "status", "running",
            "endpoints", new String[]{"/hello", "/actuator/health", "/actuator/info"}
        );
    }
}
