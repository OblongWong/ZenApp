package com.zenapp.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ============================================================================
 * SECURITY CONFIGURATION
 * ============================================================================
 * This class configures Spring Security for the ZenApp application.
 *
 * Purpose:
 * - Disables CSRF protection (for development/testing with REST API)
 * - Allows unrestricted access to all endpoints (public application)
 *
 * Note: In production, this should be configured with proper authentication
 * and authorization rules to protect sensitive endpoints.
 * ============================================================================
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for HTTP requests
     *
     * @param http - HttpSecurity object to configure security settings
     * @return SecurityFilterChain - The configured security filter chain
     * @throws Exception - If there's an error in configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // ========== SECURITY CONFIGURATION ==========
        http
                // Disable CSRF (Cross-Site Request Forgery) protection
                // This is acceptable for stateless REST APIs and development
                // In production, enable CSRF for form-based applications
                .csrf(csrf -> csrf.disable())

                // Configure authorization rules for HTTP requests
                .authorizeHttpRequests(auth -> auth
                        // Allow all requests without authentication
                        // Pattern "/**" matches all URLs in the application
                        // This makes the app publicly accessible for testing
                        .requestMatchers("/**").permitAll()
                );

        // Build and return the configured security filter chain
        return http.build();
    }
}