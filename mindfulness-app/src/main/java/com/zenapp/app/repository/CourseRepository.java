package com.zenapp.app.repository;

import com.zenapp.app.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Course entity
 * Provides database access methods for mindfulness courses
 * Spring Data JPA auto-generates implementation from method names
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    // Find courses by category (e.g., "Meditation", "Stress Management")
    List<Course> findByCategory(String category);

    // Find courses by difficulty level (BEGINNER, INTERMEDIATE, ADVANCED)
    List<Course> findByDifficultyLevel(Course.DifficultyLevel difficultyLevel);

    // Find all active courses (excludes hidden/inactive courses)
    List<Course> findByIsActiveTrue();

    // Find active courses in a specific category
    List<Course> findByCategoryAndIsActiveTrue(String category);
}