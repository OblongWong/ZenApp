package com.zenapp.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing achievement badges
 * Maps to "badges" table in database
 * Badges are awarded based on user achievements (points, streaks, completions)
 */
@Entity
@Table(name = "badges")
public class Badge {

    // ===========================================================================
    // PRIMARY KEY
    // ===========================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Integer badgeId;

    // ===========================================================================
    // BADGE INFORMATION
    // ===========================================================================

    @Column(name = "badge_name", nullable = false)
    private String badgeName;

    private String description;

    @Column(name = "icon_url")
    private String iconUrl; // URL or emoji for badge icon

    // ===========================================================================
    // ACHIEVEMENT REQUIREMENTS
    // ===========================================================================

    // Type of requirement: "points", "streak", "activities", "courses"
    @Column(name = "requirement_type")
    private String requirementType;

    // Value required to earn badge (e.g., 100 points, 7-day streak)
    @Column(name = "requirement_value")
    private Integer requirementValue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ===========================================================================
    // CONSTRUCTOR
    // ===========================================================================

    public Badge() {
        this.createdAt = LocalDateTime.now();
    }

    // ===========================================================================
    // GETTERS AND SETTERS
    // ===========================================================================

    public Integer getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Integer badgeId) {
        this.badgeId = badgeId;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(String requirementType) {
        this.requirementType = requirementType;
    }

    public Integer getRequirementValue() {
        return requirementValue;
    }

    public void setRequirementValue(Integer requirementValue) {
        this.requirementValue = requirementValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}