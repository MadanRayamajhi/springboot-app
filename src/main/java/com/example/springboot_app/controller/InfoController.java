package com.example.devops.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;
import java.util.*;

@RestController
@RequestMapping("/api/info")
public class InfoController {
    
    private final Environment env;
    
    public InfoController(Environment env) {
        this.env = env;
    }
    
    @GetMapping
    public Map<String, Object> getAppInfo() {
        return Map.of(
            "appName", "DevOps Demo Application",
            "version", "1.0.0",
            "javaVersion", System.getProperty("java.version"),
            "springVersion", env.getProperty("spring.boot.version", "3.1.5"),
            "endpoints", List.of("/health", "/api/users", "/api/products", "/api/info"),
            "timestamp", System.currentTimeMillis()
        );
    }
}