package com.zenapp.app.controller;

import com.zenapp.app.model.*;
import com.zenapp.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for admin operations
 * Provides CRUD endpoints for courses, activities, badges, and user management
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ActivityCompletionService activityCompletionService;

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private UserService userService;

    // ===========================================================================
    // COURSE MANAGEMENT
    // ===========================================================================

    // POST /api/admin/courses - Create new course
    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody Map<String, Object> request) {
        try {
            // Build course object from request data
            Course course = new Course();
            course.setCourseName((String) request.get("courseName"));
            course.setDescription((String) request.get("description"));
            course.setCategory((String) request.get("category"));

            // Parse difficulty level enum
            String difficultyStr = (String) request.get("difficultyLevel");
            course.setDifficultyLevel(Course.DifficultyLevel.valueOf(difficultyStr));

            course.setPointsReward((Integer) request.get("pointsReward"));
            course.setEstimatedDuration((Integer) request.get("estimatedDuration"));
            course.setIsActive(true);

            // Save to database
            Course savedCourse = courseService.saveCourse(course);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Course created successfully!");
            response.put("course", savedCourse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // PUT /api/admin/courses/{courseId} - Update existing course
    @PutMapping("/courses/{courseId}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Integer courseId,
            @RequestBody Map<String, Object> request) {
        try {
            // Fetch existing course
            Course course = courseService.getCourseById(courseId);

            // Update only fields that are present in request
            if (request.containsKey("courseName")) {
                course.setCourseName((String) request.get("courseName"));
            }
            if (request.containsKey("description")) {
                course.setDescription((String) request.get("description"));
            }
            if (request.containsKey("category")) {
                course.setCategory((String) request.get("category"));
            }
            if (request.containsKey("difficultyLevel")) {
                String difficultyStr = (String) request.get("difficultyLevel");
                course.setDifficultyLevel(Course.DifficultyLevel.valueOf(difficultyStr));
            }
            if (request.containsKey("pointsReward")) {
                course.setPointsReward((Integer) request.get("pointsReward"));
            }
            if (request.containsKey("estimatedDuration")) {
                course.setEstimatedDuration((Integer) request.get("estimatedDuration"));
            }

            Course updatedCourse = courseService.saveCourse(course);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Course updated successfully!");
            response.put("course", updatedCourse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // DELETE /api/admin/courses/{courseId} - Delete course
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer courseId) {
        try {
            courseService.deleteCourse(courseId);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Course deleted successfully!");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ===========================================================================
    // ACTIVITY MANAGEMENT
    // ===========================================================================

    // POST /api/admin/activities - Create new activity
    @PostMapping("/activities")
    public ResponseEntity<?> createActivity(@RequestBody Map<String, Object> request) {
        try {
            // Get parent course
            Integer courseId = (Integer) request.get("courseId");
            Course course = courseService.getCourseById(courseId);

            // Build activity object
            Activity activity = new Activity();
            activity.setCourse(course);
            activity.setActivityName((String) request.get("activityName"));
            activity.setDescription((String) request.get("description"));
            activity.setActivityType((String) request.get("activityType"));
            activity.setDuration((Integer) request.get("duration"));
            activity.setPointsReward((Integer) request.get("pointsReward"));

            // Optional media fields
            if (request.containsKey("mediaType")) {
                activity.setMediaType((String) request.get("mediaType"));
            }
            if (request.containsKey("mediaUrl")) {
                activity.setMediaUrl((String) request.get("mediaUrl"));
            }
            if (request.containsKey("orderIndex")) {
                activity.setOrderIndex((Integer) request.get("orderIndex"));
            }

            Activity savedActivity = courseService.saveActivity(activity);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Activity created successfully!");
            response.put("activity", savedActivity);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // PUT /api/admin/activities/{activityId} - Update existing activity
    @PutMapping("/activities/{activityId}")
    public ResponseEntity<?> updateActivity(
            @PathVariable Integer activityId,
            @RequestBody Map<String, Object> request) {
        try {
            Activity activity = courseService.getActivityById(activityId);

            // Update only provided fields
            if (request.containsKey("activityName")) {
                activity.setActivityName((String) request.get("activityName"));
            }
            if (request.containsKey("description")) {
                activity.setDescription((String) request.get("description"));
            }
            if (request.containsKey("activityType")) {
                activity.setActivityType((String) request.get("activityType"));
            }
            if (request.containsKey("duration")) {
                activity.setDuration((Integer) request.get("duration"));
            }
            if (request.containsKey("pointsReward")) {
                activity.setPointsReward((Integer) request.get("pointsReward"));
            }
            if (request.containsKey("mediaType")) {
                activity.setMediaType((String) request.get("mediaType"));
            }
            if (request.containsKey("mediaUrl")) {
                activity.setMediaUrl((String) request.get("mediaUrl"));
            }
            if (request.containsKey("orderIndex")) {
                activity.setOrderIndex((Integer) request.get("orderIndex"));
            }

            Activity updatedActivity = courseService.saveActivity(activity);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Activity updated successfully!");
            response.put("activity", updatedActivity);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // DELETE /api/admin/activities/{activityId} - Delete activity
    @DeleteMapping("/activities/{activityId}")
    public ResponseEntity<?> deleteActivity(@PathVariable Integer activityId) {
        try {
            courseService.deleteActivity(activityId);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Activity deleted successfully!");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ===========================================================================
    // BADGE MANAGEMENT
    // ===========================================================================

    // POST /api/admin/badges - Create new badge
    @PostMapping("/badges")
    public ResponseEntity<?> createBadge(@RequestBody Map<String, Object> request) {
        try {
            Badge badge = new Badge();
            badge.setBadgeName((String) request.get("badgeName"));
            badge.setDescription((String) request.get("description"));
            badge.setRequirementType((String) request.get("requirementType"));
            badge.setRequirementValue((Integer) request.get("requirementValue"));

            Badge savedBadge = badgeService.saveBadge(badge);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Badge created successfully!");
            response.put("badge", savedBadge);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // PUT /api/admin/badges/{badgeId} - Update existing badge
    @PutMapping("/badges/{badgeId}")
    public ResponseEntity<?> updateBadge(
            @PathVariable Integer badgeId,
            @RequestBody Map<String, Object> request) {
        try {
            Badge badge = badgeService.getBadgeById(badgeId);

            // Update only provided fields
            if (request.containsKey("badgeName")) {
                badge.setBadgeName((String) request.get("badgeName"));
            }
            if (request.containsKey("description")) {
                badge.setDescription((String) request.get("description"));
            }
            if (request.containsKey("requirementType")) {
                badge.setRequirementType((String) request.get("requirementType"));
            }
            if (request.containsKey("requirementValue")) {
                badge.setRequirementValue((Integer) request.get("requirementValue"));
            }

            Badge updatedBadge = badgeService.saveBadge(badge);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Badge updated successfully!");
            response.put("badge", updatedBadge);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // DELETE /api/admin/badges/{badgeId} - Delete badge
    @DeleteMapping("/badges/{badgeId}")
    public ResponseEntity<?> deleteBadge(@PathVariable Integer badgeId) {
        try {
            badgeService.deleteBadge(badgeId);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Badge deleted successfully!");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // GET /api/admin/badges - Get all badges
    @GetMapping("/badges")
    public ResponseEntity<List<Badge>> getAllBadges() {
        List<Badge> badges = badgeService.getAllBadges();
        return ResponseEntity.ok(badges);
    }

    // ===========================================================================
    // USER MANAGEMENT
    // ===========================================================================

    // GET /api/admin/users - Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}