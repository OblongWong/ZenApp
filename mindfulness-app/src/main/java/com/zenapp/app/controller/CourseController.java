package com.zenapp.app.controller;

import com.zenapp.app.model.Activity;
import com.zenapp.app.model.Course;
import com.zenapp.app.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for course and activity retrieval
 * Provides public endpoints for browsing courses and activities
 */
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // ===========================================================================
    // COURSE RETRIEVAL
    // ===========================================================================

    // GET /api/courses - Get all active courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllActiveCourses();
        return ResponseEntity.ok(courses);
    }

    // GET /api/courses/{courseId} - Get specific course by ID
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer courseId) {
        Course course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(course);
    }

    // GET /api/courses/category/{category} - Get courses by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Course>> getCoursesByCategory(@PathVariable String category) {
        List<Course> courses = courseService.getCoursesByCategory(category);
        return ResponseEntity.ok(courses);
    }

    // ===========================================================================
    // ACTIVITY RETRIEVAL
    // ===========================================================================

    // GET /api/courses/{courseId}/activities - Get all activities in a course
    @GetMapping("/{courseId}/activities")
    public ResponseEntity<List<Activity>> getCourseActivities(@PathVariable Integer courseId) {
        List<Activity> activities = courseService.getActivitiesForCourse(courseId);
        return ResponseEntity.ok(activities);
    }

    // GET /api/courses/activities/{activityId} - Get specific activity by ID
    @GetMapping("/activities/{activityId}")
    public ResponseEntity<Activity> getActivity(@PathVariable Integer activityId) {
        Activity activity = courseService.getActivityById(activityId);
        return ResponseEntity.ok(activity);
    }
}