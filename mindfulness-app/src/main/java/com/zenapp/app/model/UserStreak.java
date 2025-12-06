package com.zenapp.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entity class representing user activity streaks
 * Maps to "user_streaks" table in database
 * Tracks consecutive days of activity completion for gamification
 */
@Entity
@Table(name = "user_streaks")
public class UserStreak {

    // ===========================================================================
    // PRIMARY KEY
    // ===========================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "streak_id")
    private Integer streakId;

    // ===========================================================================
    // RELATIONSHIPS
    // ===========================================================================

    // One-to-one with User (each user has one streak record)
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // ===========================================================================
    // STREAK DATA
    // ===========================================================================

    @Column(name = "current_streak")
    private Integer currentStreak = 0; // Current consecutive days

    @Column(name = "longest_streak")
    private Integer longestStreak = 0; // Best streak ever achieved

    @Column(name = "last_activity_date")
    private LocalDate lastActivityDate; // Last day activity was completed

    // ===========================================================================
    // CONSTRUCTORS
    // ===========================================================================

    public UserStreak() {
    }

    public UserStreak(User user) {
        this.user = user;
        this.currentStreak = 0;
        this.longestStreak = 0;
    }

    // ===========================================================================
    // GETTERS AND SETTERS
    // ===========================================================================

    public Integer getStreakId() {
        return streakId;
    }

    public void setStreakId(Integer streakId) {
        this.streakId = streakId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    // Auto-updates longest streak when current streak increases
    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
        if (currentStreak > this.longestStreak) {
            this.longestStreak = currentStreak;
        }
    }

    public Integer getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(Integer longestStreak) {
        this.longestStreak = longestStreak;
    }

    public LocalDate getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(LocalDate lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }
}