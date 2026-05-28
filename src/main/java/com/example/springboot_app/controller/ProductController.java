package com.example.springboot_app.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    private final Map<Long, Map<String, Object>> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public ProductController() {
        // Add sample data
        products.put(1L, Map.of("id", 1L, "name", "Laptop", "price", 999.99));
        products.put(2L, Map.of("id", 2L, "name", "Mouse", "price", 29.99));
        idGenerator.set(3);
    }
    
    // GET all products
    @GetMapping
    public List<Map<String, Object>> getAllProducts() {
        return new ArrayList<>(products.values());
    }
    
    // GET product by id
    @GetMapping("/{id}")
    public Map<String, Object> getProduct(@PathVariable Long id) {
        return products.get(id);
    }
    
    // POST create product
    @PostMapping
    public Map<String, Object> createProduct(@RequestBody Map<String, Object> product) {
        Long id = idGenerator.getAndIncrement();
        product.put("id", id);
        products.put(id, product);
        return Map.of("message", "Product created", "product", product);
    }
    
    // DELETE product
    @DeleteMapping("/{id}")
    public Map<String, String> deleteProduct(@PathVariable Long id) {
        products.remove(id);
        return Map.of("message", "Product deleted", "id", String.valueOf(id));
    }
}
