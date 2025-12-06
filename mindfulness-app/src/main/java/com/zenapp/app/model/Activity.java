package com.zenapp.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity class representing a mindfulness activity
 * Maps to "activities" table in database
 * Each activity belongs to a course and contains meditation/exercise content
 */
@Entity
@Table(name = "activities")
public class Activity {

    // ===========================================================================
    // PRIMARY KEY
    // ===========================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Integer activityId;

    // ===========================================================================
    // RELATIONSHIPS
    // ===========================================================================

    // Many activities belong to one course
    // JsonIgnore prevents infinite recursion in JSON serialization
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // ===========================================================================
    // BASIC FIELDS
    // ===========================================================================

    @Column(name = "activity_name", nullable = false)
    private String activityName;

    private String description;

    @Column(name = "activity_type")
    private String activityType; // e.g., "Meditation", "Breathing", "Relaxation"

    private Integer duration; // Duration in minutes

    @Column(name = "points_reward")
    private Integer pointsReward = 5; // Points awarded on completion

    @Column(name = "order_index")
    private Integer orderIndex; // Order within course

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ===========================================================================
    // CONTENT FIELDS
    // ===========================================================================

    @Column(name = "content_url")
    private String contentUrl; // Legacy URL field

    // JSON content with instructions, benefits, timer settings
    @Column(name = "content", columnDefinition = "json")
    private String content;

    // Media fields for video/audio
    @Column(name = "media_type")
    private String mediaType; // e.g., "video", "audio"

    @Column(name = "media_url")
    private String mediaUrl; // YouTube embed URL or audio file

    // ===========================================================================
    // CONSTRUCTOR
    // ===========================================================================

    public Activity() {
        this.createdAt = LocalDateTime.now();
    }

    // ===========================================================================
    // GETTERS AND SETTERS
    // ===========================================================================

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPointsReward() {
        return pointsReward;
    }

    public void setPointsReward(Integer pointsReward) {
        this.pointsReward = pointsReward;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}