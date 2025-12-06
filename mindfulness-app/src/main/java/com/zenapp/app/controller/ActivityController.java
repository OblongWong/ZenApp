package com.zenapp.app.controller;

import com.zenapp.app.model.ActivityCompletion;
import com.zenapp.app.service.ActivityCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for activity completion operations
 * Handles endpoints for completing, tracking, and resetting user activities
 */
@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityCompletionService completionService;

    // ===========================================================================
    // COMPLETE ACTIVITY
    // ===========================================================================
    // POST /api/activities/complete
    // Marks an activity as completed and awards points to user
    @PostMapping("/complete")
    public ResponseEntity<?> completeActivity(@RequestBody Map<String, Integer> request) {
        try {
            // Extract user and activity IDs from request
            Integer userId = request.get("userId");
            Integer activityId = request.get("activityId");

            // Process activity completion (awards points, updates streak, checks badges)
            ActivityCompletion completion = completionService.completeActivity(userId, activityId);

            // Build success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Activity completed successfully!");
            response.put("pointsEarned", completion.getPointsEarned());
            response.put("activityName", completion.getActivity().getActivityName());
            response.put("completedAt", completion.getCompletedAt());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Return error if activity already completed or user/activity not found
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ===========================================================================
    // GET USER COMPLETIONS
    // ===========================================================================
    // GET /api/activities/user/{userId}
    // Returns all activities completed by a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ActivityCompletion>> getUserCompletions(@PathVariable Integer userId) {
        List<ActivityCompletion> completions = completionService.getUserCompletions(userId);
        return ResponseEntity.ok(completions);
    }

    // ===========================================================================
    // CHECK COMPLETION STATUS
    // ===========================================================================
    // GET /api/activities/check/{userId}/{activityId}
    // Checks if user has completed a specific activity
    @GetMapping("/check/{userId}/{activityId}")
    public ResponseEntity<?> checkCompletion(
            @PathVariable Integer userId,
            @PathVariable Integer activityId) {

        // Query database for completion status
        boolean completed = completionService.hasUserCompletedActivity(userId, activityId);

        // Return completion status
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("activityId", activityId);
        response.put("completed", completed);

        return ResponseEntity.ok(response);
    }

    // ===========================================================================
    // GET COURSE PROGRESS
    // ===========================================================================
    // GET /api/activities/progress/{userId}/{courseId}
    // Calculates and returns user's progress percentage for a course
    @GetMapping("/progress/{userId}/{courseId}")
    public ResponseEntity<?> getCourseProgress(
            @PathVariable Integer userId,
            @PathVariable Integer courseId) {

        // Calculate progress as percentage (completed activities / total activities * 100)
        double progress = completionService.getCourseProgress(userId, courseId);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("courseId", courseId);
        response.put("progressPercent", progress);

        return ResponseEntity.ok(response);
    }

    // ===========================================================================
    // UNCOMPLETE ACTIVITY (UNDO)
    // ===========================================================================
    // DELETE /api/activities/uncomplete
    // Removes activity completion and deducts points from user
    @DeleteMapping("/uncomplete")
    public ResponseEntity<?> uncompleteActivity(@RequestBody Map<String, Integer> request) {
        try {
            Integer userId = request.get("userId");
            Integer activityId = request.get("activityId");

            // Remove completion record and deduct points
            completionService.uncompleteActivity(userId, activityId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Activity marked as incomplete!");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ===========================================================================
    // RESET COURSE PROGRESS
    // ===========================================================================
    // DELETE /api/activities/reset-course
    // Removes all activity completions for a course and deducts all earned points
    @DeleteMapping("/reset-course")
    public ResponseEntity<?> resetCourseProgress(@RequestBody Map<String, Integer> request) {
        try {
            Integer userId = request.get("userId");
            Integer courseId = request.get("courseId");

            // Delete all completions for this course
            completionService.resetCourseProgress(userId, courseId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Course progress reset successfully!");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}