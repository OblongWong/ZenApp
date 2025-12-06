package com.zenapp.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing user-earned badges
 * Maps to "user_badges" table in database
 * Join table linking users to their earned badges with timestamp
 */
@Entity
@Table(name = "user_badges")
public class UserBadge {

    // ===========================================================================
    // PRIMARY KEY
    // ===========================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_badge_id")
    private Integer userBadgeId;

    // ===========================================================================
    // RELATIONSHIPS
    // ===========================================================================

    // User who earned the badge
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Badge that was earned
    @ManyToOne
    @JoinColumn(name = "badge_id", nullable = false)
    private Badge badge;

    // ===========================================================================
    // TIMESTAMP
    // ===========================================================================

    @Column(name = "earned_at")
    private LocalDateTime earnedAt; // When the badge was earned

    // ===========================================================================
    // CONSTRUCTOR
    // ===========================================================================

    public UserBadge() {
        this.earnedAt = LocalDateTime.now();
    }

    // ===========================================================================
    // GETTERS AND SETTERS
    // ===========================================================================

    public Integer getUserBadgeId() {
        return userBadgeId;
    }

    public void setUserBadgeId(Integer userBadgeId) {
        this.userBadgeId = userBadgeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public LocalDateTime getEarnedAt() {
        return earnedAt;
    }

    public void setEarnedAt(LocalDateTime earnedAt) {
        this.earnedAt = earnedAt;
    }
}