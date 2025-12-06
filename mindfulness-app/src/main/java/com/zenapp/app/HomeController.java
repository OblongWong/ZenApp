package com.zenapp.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple controller for health check endpoints
 * Provides basic responses to verify server is running
 */
@RestController
public class HomeController {

    // GET / - Root endpoint to verify server is alive
    @GetMapping("/")
    public String home() {
        return "Welcome to the Mindfulness App! Your server is running.";
    }

    // GET /hello - Additional health check endpoint
    @GetMapping("/hello")
    public String hello() {
        return "Hello! The app is working!";
    }
}