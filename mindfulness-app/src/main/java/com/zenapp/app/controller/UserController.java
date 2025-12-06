package com.zenapp.app.controller;

import com.zenapp.app.model.User;
import com.zenapp.app.model.UserBadge;
import com.zenapp.app.service.BadgeService;
import com.zenapp.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for user management and authentication
 * Handles registration, login, profile management, and account settings
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BadgeService badgeService;

    // ===========================================================================
    // AUTHENTICATION
    // ===========================================================================

    // POST /api/users/register - Register new user account
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        try {
            // Extract registration data
            String username = request.get("username");
            String email = request.get("email");
            String password = request.get("password");
            String fullName = request.get("fullName");

            // Create user account (checks for duplicate username/email)
            User newUser = userService.registerUser(username, email, password, fullName);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully!");
            response.put("userId", newUser.getUserId());
            response.put("username", newUser.getUsername());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // POST /api/users/login - Authenticate user credentials
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            // Verify credentials and return user data
            User user = userService.loginUser(username, password);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful!");
            response.put("userId", user.getUserId());
            response.put("username", user.getUsername());
            response.put("fullName", user.getFullName());
            response.put("totalPoints", user.getTotalPoints());
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ===========================================================================
    // PROFILE RETRIEVAL
    // ===========================================================================

    // GET /api/users/{userId} - Get user profile information
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Integer userId) {
        try {
            User user = userService.getUserById(userId);

            Map<String, Object> profile = new HashMap<>();
            profile.put("userId", user.getUserId());
            profile.put("username", user.getUsername());
            profile.put("email", user.getEmail());
            profile.put("fullName", user.getFullName());
            profile.put("totalPoints", user.getTotalPoints());
            profile.put("role", user.getRole());
            profile.put("createdAt", user.getCreatedAt());

            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // GET /api/users/{userId}/points - Get user's total points
    @GetMapping("/{userId}/points")
    public ResponseEntity<?> getUserPoints(@PathVariable Integer userId) {
        Integer points = userService.getUserPoints(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("totalPoints", points);

        return ResponseEntity.ok(response);
    }

    // GET /api/users/{userId}/badges - Get all badges earned by user
    @GetMapping("/{userId}/badges")
    public ResponseEntity<List<UserBadge>> getUserBadges(@PathVariable Integer userId) {
        List<UserBadge> badges = badgeService.getUserBadges(userId);
        return ResponseEntity.ok(badges);
    }

    // ===========================================================================
    // PROFILE UPDATES
    // ===========================================================================

    // PUT /api/users/{userId}/email - Update user's email address
    @PutMapping("/{userId}/email")
    public ResponseEntity<?> updateEmail(@PathVariable Integer userId, @RequestBody Map<String, String> request) {
        try {
            String newEmail = request.get("email");
            User user = userService.updateEmail(userId, newEmail);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Email updated successfully!");
            response.put("email", user.getEmail());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // PUT /api/users/{userId}/password - Update user's password
    @PutMapping("/{userId}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Integer userId, @RequestBody Map<String, String> request) {
        try {
            String currentPassword = request.get("currentPassword");
            String newPassword = request.get("newPassword");

            // Verify current password before updating
            userService.updatePassword(userId, currentPassword, newPassword);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Password updated successfully!");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // PUT /api/users/{userId}/fullname - Update user's full name
    @PutMapping("/{userId}/fullname")
    public ResponseEntity<?> updateFullName(@PathVariable Integer userId, @RequestBody Map<String, String> request) {
        try {
            String newFullName = request.get("fullName");
            User user = userService.updateFullName(userId, newFullName);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Full name updated successfully!");
            response.put("fullName", user.getFullName());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // PUT /api/users/{userId}/profile-icon - Update user's profile icon
    @PutMapping("/{userId}/profile-icon")
    public ResponseEntity<?> updateProfileIcon(@PathVariable Integer userId, @RequestBody Map<String, String> request) {
        try {
            String profileIcon = request.get("profileIcon");
            User user = userService.getUserById(userId);
            user.setProfileIcon(profileIcon);
            userService.saveUser(user);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Profile icon updated successfully!");
            response.put("profileIcon", user.getProfileIcon());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ===========================================================================
    // ACCOUNT DELETION
    // ===========================================================================

    // DELETE /api/users/{userId} - Delete user account
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer userId, @RequestBody Map<String, String> request) {
        try {
            // Verify password before deletion
            String password = request.get("password");
            userService.deleteAccount(userId, password);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Account deleted successfully!");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}