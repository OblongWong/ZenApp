package com.zenapp.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing a completed activity
 * Maps to "activity_completions" table in database
 * Tracks when users complete activities and points earned
 */
@Entity
@Table(name = "activity_completions")
public class ActivityCompletion {

    // ===========================================================================
    // PRIMARY KEY
    // ===========================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "completion_id")
    private Integer completionId;

    // ===========================================================================
    // RELATIONSHIPS
    // ===========================================================================

    // User who completed the activity
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Activity that was completed
    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    // Course containing the activity
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // ===========================================================================
    // COMPLETION DATA
    // ===========================================================================

    @Column(name = "points_earned")
    private Integer pointsEarned = 0; // Points awarded for this completion

    @Column(name = "completed_at")
    private LocalDateTime completedAt; // Timestamp of completion

    private String notes; // Optional user notes

    // ===========================================================================
    // CONSTRUCTOR
    // ===========================================================================

    public ActivityCompletion() {
        this.completedAt = LocalDateTime.now();
    }

    // ===========================================================================
    // GETTERS AND SETTERS
    // ===========================================================================

    public Integer getCompletionId() {
        return completionId;
    }

    public void setCompletionId(Integer completionId) {
        this.completionId = completionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(Integer pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}