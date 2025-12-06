package com.zenapp.app.repository;

import com.zenapp.app.model.Activity;
import com.zenapp.app.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Activity entity
 * Provides database access methods for mindfulness activities
 * Spring Data JPA auto-generates implementation from method names
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    // Find all activities for a specific course (using Course object)
    List<Activity> findByCourse(Course course);

    // Find all activities for a course by course ID
    List<Activity> findByCourse_CourseId(Integer courseId);

    // Find activities by type (e.g., "Meditation", "Breathing")
    List<Activity> findByActivityType(String activityType);

    // Find activities for a course, sorted by order_index (ascending)
    List<Activity> findByCourse_CourseIdOrderByOrderIndexAsc(Integer courseId);
}