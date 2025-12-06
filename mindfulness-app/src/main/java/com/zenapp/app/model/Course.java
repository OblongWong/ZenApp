package com.zenapp.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class representing a mindfulness course
 * Maps to "courses" table in database
 * Each course contains multiple activities and has difficulty level
 */
@Entity
@Table(name = "courses")
public class Course {

    // ===========================================================================
    // PRIMARY KEY
    // ===========================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    // ===========================================================================
    // COURSE INFORMATION
    // ===========================================================================

    @Column(name = "course_name", nullable = false)
    private String courseName;

    private String description;

    private String category; // e.g., "Meditation", "Stress Management"

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level")
    private DifficultyLevel difficultyLevel; // BEGINNER, INTERMEDIATE, ADVANCED

    @Column(name = "points_reward")
    private Integer pointsReward = 10; // Bonus points for completing course

    @Column(name = "estimated_duration")
    private Integer estimatedDuration; // Total minutes to complete

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_active")
    private Boolean isActive = true; // Hide inactive courses

    // ===========================================================================
    // RELATIONSHIPS
    // ===========================================================================

    // One course has many activities
    // JsonIgnore prevents infinite recursion in JSON serialization
    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Activity> activities;

    // ===========================================================================
    // ENUMS
    // ===========================================================================

    public enum DifficultyLevel {
        BEGINNER, INTERMEDIATE, ADVANCED
    }

    // ===========================================================================
    // CONSTRUCTOR
    // ===========================================================================

    public Course() {
        this.createdAt = LocalDateTime.now();
    }

    // ===========================================================================
    // GETTERS AND SETTERS
    // ===========================================================================

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getPointsReward() {
        return pointsReward;
    }

    public void setPointsReward(Integer pointsReward) {
        this.pointsReward = pointsReward;
    }

    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Integer estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}