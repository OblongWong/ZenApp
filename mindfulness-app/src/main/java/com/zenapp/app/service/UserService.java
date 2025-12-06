package com.zenapp.app.service;

import com.zenapp.app.model.User;
import com.zenapp.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for user account management
 * Handles registration, authentication, profile updates, and account operations
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StreakService streakService;

    // ===========================================================================
    // REGISTRATION & AUTHENTICATION
    // ===========================================================================

    // Register a new user account
    public User registerUser(String username, String email, String password, String fullName) {
        // Validate username is unique
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists!");
        }

        // Validate email is unique
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists!");
        }

        // Create new user account
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPasswordHash(password); // NOTE: In production, use BCrypt or similar
        newUser.setFullName(fullName);
        newUser.setRole(User.Role.USER);
        newUser.setTotalPoints(0);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setIsActive(true);

        User savedUser = userRepository.save(newUser);

        // Initialize streak record for new user
        streakService.updateStreak(savedUser.getUserId());

        return savedUser;
    }

    // Authenticate user credentials and login
    public User loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        User user = userOpt.get();

        // Verify password (NOTE: In production, use BCrypt.matches())
        if (!user.getPasswordHash().equals(password)) {
            throw new RuntimeException("Invalid password!");
        }

        // Update last login timestamp
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Update streak on login
        streakService.updateStreak(user.getUserId());

        return user;
    }

    // ===========================================================================
    // USER RETRIEVAL
    // ===========================================================================

    // Get user by ID
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    // Get user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    // Get all users (admin only)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ===========================================================================
    // POINTS MANAGEMENT
    // ===========================================================================

    // Get user's total points
    public Integer getUserPoints(Integer userId) {
        User user = getUserById(userId);
        return user.getTotalPoints();
    }

    // Add points to user account
    public User addPoints(Integer userId, Integer points) {
        User user = getUserById(userId);
        user.setTotalPoints(user.getTotalPoints() + points);
        return userRepository.save(user);
    }

    // ===========================================================================
    // PROFILE UPDATES
    // ===========================================================================

    // Update user's email address
    public User updateEmail(Integer userId, String newEmail) {
        // Check if email is already taken by another user
        if (userRepository.existsByEmail(newEmail)) {
            User existingUser = userRepository.findByEmail(newEmail).orElse(null);
            if (existingUser != null && !existingUser.getUserId().equals(userId)) {
                throw new RuntimeException("Email already in use!");
            }
        }

        User user = getUserById(userId);
        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    // Update user's password
    public void updatePassword(Integer userId, String currentPassword, String newPassword) {
        User user = getUserById(userId);

        // Verify current password before changing
        if (!user.getPasswordHash().equals(currentPassword)) {
            throw new RuntimeException("Current password is incorrect!");
        }

        // Update to new password
        user.setPasswordHash(newPassword);
        userRepository.save(user);
    }

    // Update user's full name
    public User updateFullName(Integer userId, String newFullName) {
        User user = getUserById(userId);
        user.setFullName(newFullName);
        return userRepository.save(user);
    }

    // Save user (for profile icon and other updates)
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // ===========================================================================
    // ACCOUNT DELETION
    // ===========================================================================

    // Delete user account permanently
    public void deleteAccount(Integer userId, String password) {
        User user = getUserById(userId);

        // Verify password before deletion for security
        if (!user.getPasswordHash().equals(password)) {
            throw new RuntimeException("Password is incorrect!");
        }

        // Delete user (cascade deletes related data: completions, badges, streak)
        userRepository.delete(user);
    }
}