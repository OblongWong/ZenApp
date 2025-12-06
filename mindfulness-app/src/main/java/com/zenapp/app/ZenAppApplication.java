package com.zenapp.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for ZenApp
 * Entry point for Spring Boot application
 * Runs embedded Tomcat server on port 8080
 */

// ===========================================================================
// APPLICATION LINKS
// ===========================================================================

// MAIN PAGES
// http://localhost:8080/login.html          - User login and registration
// http://localhost:8080/home.html           - Course dashboard
// http://localhost:8080/course.html         - Course details and activities
// http://localhost:8080/profile.html        - User profile and badges

// ADMIN & ANALYTICS
// http://localhost:8080/admin.html          - Admin panel (course/activity/badge management)
// http://localhost:8080/analytics.html      - Analytics dashboard and reports

// SETTINGS
// http://localhost:8080/settings.html       - User settings (dark mode, sounds, profile)

// HEALTH CHECK
// http://localhost:8080/                    - Server status check
// http://localhost:8080/hello               - Health check endpoint

// ===========================================================================

@SpringBootApplication
public class ZenAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZenAppApplication.class, args);
    }
}