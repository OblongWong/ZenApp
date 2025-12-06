package com.zenapp.app.service;

import com.zenapp.app.model.Activity;
import com.zenapp.app.model.Course;
import com.zenapp.app.repository.ActivityRepository;
import com.zenapp.app.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for course and activity management
 * Handles course retrieval, activity management, and admin operations
 */
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ActivityRepository activityRepository;

    // ===========================================================================
    // COURSE RETRIEVAL
    // ===========================================================================

    // Get all active courses (excludes hidden courses)
    public List<Course> getAllActiveCourses() {
        return courseRepository.findByIsActiveTrue();
    }

    // Get all courses including inactive ones
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get specific course by ID
    public Course getCourseById(Integer courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));
    }

    // Get active courses by category (e.g., "Meditation", "Stress Management")
    public List<Course> getCoursesByCategory(String category) {
        return courseRepository.findByCategoryAndIsActiveTrue(category);
    }

    // Get courses by difficulty level (BEGINNER, INTERMEDIATE, ADVANCED)
    public List<Course> getCoursesByDifficulty(Course.DifficultyLevel level) {
        return courseRepository.findByDifficultyLevel(level);
    }

    // ===========================================================================
    // ACTIVITY RETRIEVAL
    // ===========================================================================

    // Get all activities for a course, sorted by order
    public List<Activity> getActivitiesForCourse(Integer courseId) {
        return activityRepository.findByCourse_CourseIdOrderByOrderIndexAsc(courseId);
    }

    // Get specific activity by ID
    public Activity getActivityById(Integer activityId) {
        return activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found!"));
    }

    // Count total activities in a course
    public int countActivitiesInCourse(Integer courseId) {
        return activityRepository.findByCourse_CourseId(courseId).size();
    }

    // ===========================================================================
    // ADMIN OPERATIONS - COURSES
    // ===========================================================================

    // Create or update a course
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    // Delete a course from the system
    public void deleteCourse(Integer courseId) {
        courseRepository.deleteById(courseId);
    }

    // ===========================================================================
    // ADMIN OPERATIONS - ACTIVITIES
    // ===========================================================================

    // Create or update an activity
    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    // Delete an activity from the system
    public void deleteActivity(Integer activityId) {
        activityRepository.deleteById(activityId);
    }
}