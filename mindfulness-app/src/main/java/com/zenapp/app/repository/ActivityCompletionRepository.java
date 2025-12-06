package com.zenapp.app.repository;

import com.zenapp.app.model.ActivityCompletion;
import com.zenapp.app.model.User;
import com.zenapp.app.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ActivityCompletion entity
 * Provides database access methods for activity completion records
 * Spring Data JPA auto-generates implementation from method names
 */
@Repository
public interface ActivityCompletionRepository extends JpaRepository<ActivityCompletion, Integer> {

    // Find all completions for a user (using User object)
    List<ActivityCompletion> findByUser(User user);

    // Find all completions for a user by user ID
    List<ActivityCompletion> findByUser_UserId(Integer userId);

    // Find completion record for specific user and activity (using objects)
    Optional<ActivityCompletion> findByUserAndActivity(User user, Activity activity);

    // Check if user has completed activity (returns true/false)
    boolean existsByUser_UserIdAndActivity_ActivityId(Integer userId, Integer activityId);

    // Find specific completion by user and activity IDs
    Optional<ActivityCompletion> findByUser_UserIdAndActivity_ActivityId(Integer userId, Integer activityId);

    // Count total completions for a user
    long countByUser_UserId(Integer userId);

    // Find all completions for a specific course by user
    List<ActivityCompletion> findByUser_UserIdAndCourse_CourseId(Integer userId, Integer courseId);
}