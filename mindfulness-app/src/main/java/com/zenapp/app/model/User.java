package com.zenapp.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing a user account
 * Maps to "users" table in database
 * Stores authentication credentials, profile info, and gamification data
 */
@Entity
@Table(name = "users")
public class User {

    // ===========================================================================
    // PRIMARY KEY
    // ===========================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    // ===========================================================================
    // AUTHENTICATION
    // ===========================================================================

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash; // Hashed password for security

    // ===========================================================================
    // PROFILE INFORMATION
    // ===========================================================================

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "profile_icon")
    private String profileIcon = "🧘"; // Emoji icon for user profile

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // USER or ADMIN

    // ===========================================================================
    // GAMIFICATION
    // ===========================================================================

    @Column(name = "total_points")
    private Integer totalPoints = 0; // Points earned from activity completions

    // ===========================================================================
    // METADATA
    // ===========================================================================

    @Column(name = "created_at")
    private LocalDateTime createdAt; // Account registration timestamp

    @Column(name = "last_login")
    private LocalDateTime lastLogin; // Last login timestamp

    @Column(name = "is_active")
    private Boolean isActive = true; // Enable/disable account

    // ===========================================================================
    // ENUMS
    // ===========================================================================

    public enum Role {
        USER, ADMIN
    }

    // ===========================================================================
    // CONSTRUCTOR
    // ===========================================================================

    public User() {
        this.createdAt = LocalDateTime.now();
    }

    // ===========================================================================
    // GETTERS AND SETTERS
    // ===========================================================================

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(String profileIcon) {
        this.profileIcon = profileIcon;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}