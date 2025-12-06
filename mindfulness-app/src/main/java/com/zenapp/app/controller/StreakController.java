package com.zenapp.app.controller;

import com.zenapp.app.model.UserStreak;
import com.zenapp.app.service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for user streak management
 * Handles streak tracking, updates, and leaderboards
 */
@RestController
@RequestMapping("/api/streaks")
public class StreakController {

    @Autowired
    private StreakService streakService;

    // ===========================================================================
    // STREAK INFORMATION
    // ===========================================================================

    // GET /api/streaks/user/{userId} - Get user's current streak data
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserStreak(@PathVariable Integer userId) {
        try {
            // Fetch streak record (validates if still active)
            UserStreak streak = streakService.getUserStreak(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("currentStreak", streak.getCurrentStreak());
            response.put("longestStreak", streak.getLongestStreak());
            response.put("lastActivityDate", streak.getLastActivityDate());
            response.put("hasActivityToday", streakService.hasActivityToday(userId));

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ===========================================================================
    // STREAK UPDATES
    // ===========================================================================

    // POST /api/streaks/update/{userId} - Update streak after activity completion
    @PostMapping("/update/{userId}")
    public ResponseEntity<?> updateStreak(@PathVariable Integer userId) {
        try {
            // Increment streak if consecutive day, reset if broken
            UserStreak streak = streakService.updateStreak(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Streak updated successfully!");
            response.put("currentStreak", streak.getCurrentStreak());
            response.put("longestStreak", streak.getLongestStreak());
            response.put("lastActivityDate", streak.getLastActivityDate());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ===========================================================================
    // LEADERBOARDS
    // ===========================================================================

    // GET /api/streaks/leaderboard/current - Get top 10 users by current streak
    @GetMapping("/leaderboard/current")
    public ResponseEntity<List<UserStreak>> getCurrentStreakLeaderboard() {
        List<UserStreak> topStreaks = streakService.getTopCurrentStreaks();
        return ResponseEntity.ok(topStreaks);
    }

    // GET /api/streaks/leaderboard/longest - Get top 10 users by longest streak
    @GetMapping("/leaderboard/longest")
    public ResponseEntity<List<UserStreak>> getLongestStreakLeaderboard() {
        List<UserStreak> topStreaks = streakService.getTopLongestStreaks();
        return ResponseEntity.ok(topStreaks);
    }
}