package com.zenapp.app.controller;

import com.zenapp.app.model.ActivityCompletion;
import com.zenapp.app.model.User;
import com.zenapp.app.model.Course;
import com.zenapp.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for analytics and reporting
 * Provides endpoints for dashboard statistics, trends, and data visualization
 */
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityCompletionRepository activityCompletionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private UserBadgeRepository userBadgeRepository;

    @Autowired
    private UserStreakRepository userStreakRepository;

    // ===========================================================================
    // OVERVIEW STATISTICS
    // ===========================================================================

    // GET /api/analytics/overview - Get dashboard summary statistics
    @GetMapping("/overview")
    public ResponseEntity<?> getOverviewStats() {
        try {
            Map<String, Object> stats = new HashMap<>();

            // Count total records
            long totalUsers = userRepository.count();
            long totalActivities = activityCompletionRepository.count();
            long totalCourses = courseRepository.count();
            long totalBadges = badgeRepository.count();
            long totalBadgesEarned = userBadgeRepository.count();

            // Sum all user points
            List<User> users = userRepository.findAll();
            int totalPoints = users.stream()
                    .mapToInt(User::getTotalPoints)
                    .sum();

            // Count users registered in last 7 days
            LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
            long newUsersThisWeek = users.stream()
                    .filter(u -> u.getCreatedAt() != null && u.getCreatedAt().isAfter(oneWeekAgo))
                    .count();

            // Count activities completed today
            LocalDate today = LocalDate.now();
            long activitiesToday = activityCompletionRepository.findAll().stream()
                    .filter(ac -> ac.getCompletedAt() != null &&
                            ac.getCompletedAt().toLocalDate().equals(today))
                    .count();

            stats.put("totalUsers", totalUsers);
            stats.put("totalActivities", totalActivities);
            stats.put("totalCourses", totalCourses);
            stats.put("totalBadges", totalBadges);
            stats.put("totalBadgesEarned", totalBadgesEarned);
            stats.put("totalPoints", totalPoints);
            stats.put("newUsersThisWeek", newUsersThisWeek);
            stats.put("activitiesToday", activitiesToday);

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ===========================================================================
    // LEADERBOARDS
    // ===========================================================================

    // GET /api/analytics/top-users - Get top users by points
    @GetMapping("/top-users")
    public ResponseEntity<?> getTopUsers(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<User> allUsers = userRepository.findAll();

            // Sort by points descending and take top N
            List<Map<String, Object>> topUsers = allUsers.stream()
                    .sorted((u1, u2) -> Integer.compare(u2.getTotalPoints(), u1.getTotalPoints()))
                    .limit(limit)
                    .map(user -> {
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("userId", user.getUserId());
                        userMap.put("username", user.getUsername());
                        userMap.put("fullName", user.getFullName());
                        userMap.put("totalPoints", user.getTotalPoints());

                        // Count completions for this user
                        long completions = activityCompletionRepository.countByUser_UserId(user.getUserId());
                        userMap.put("completions", completions);

                        // Count badges earned
                        long badges = userBadgeRepository.countByUser_UserId(user.getUserId());
                        userMap.put("badges", badges);

                        return userMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(topUsers);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ===========================================================================
    // COURSE ANALYTICS
    // ===========================================================================

    // GET /api/analytics/popular-courses - Get courses by completion count
    @GetMapping("/popular-courses")
    public ResponseEntity<?> getPopularCourses(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<Course> allCourses = courseRepository.findAll();

            List<Map<String, Object>> popularCourses = allCourses.stream()
                    .map(course -> {
                        Map<String, Object> courseMap = new HashMap<>();
                        courseMap.put("courseId", course.getCourseId());
                        courseMap.put("courseName", course.getCourseName());
                        courseMap.put("category", course.getCategory());
                        courseMap.put("difficulty", course.getDifficultyLevel());

                        // Count total completions for this course
                        List<ActivityCompletion> completions = activityCompletionRepository
                                .findAll().stream()
                                .filter(ac -> ac.getCourse().getCourseId().equals(course.getCourseId()))
                                .collect(Collectors.toList());

                        courseMap.put("completions", completions.size());

                        // Count unique users who completed activities in this course
                        long uniqueUsers = completions.stream()
                                .map(ac -> ac.getUser().getUserId())
                                .distinct()
                                .count();
                        courseMap.put("uniqueUsers", uniqueUsers);

                        return courseMap;
                    })
                    .sorted((c1, c2) -> Integer.compare(
                            (Integer) c2.get("completions"),
                            (Integer) c1.get("completions")))
                    .limit(limit)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(popularCourses);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // GET /api/analytics/course-categories - Get course distribution by category
    @GetMapping("/course-categories")
    public ResponseEntity<?> getCourseCategoryDistribution() {
        try {
            List<Course> courses = courseRepository.findAll();

            // Group courses by category and count
            Map<String, Long> categoryCount = courses.stream()
                    .collect(Collectors.groupingBy(
                            course -> course.getCategory() != null ? course.getCategory() : "Uncategorized",
                            Collectors.counting()
                    ));

            List<Map<String, Object>> distribution = categoryCount.entrySet().stream()
                    .map(entry -> {
                        Map<String, Object> item = new HashMap<>();
                        item.put("category", entry.getKey());
                        item.put("count", entry.getValue());
                        return item;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(distribution);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // GET /api/analytics/completion-rates - Get average completion rate per course
    @GetMapping("/completion-rates")
    public ResponseEntity<?> getCompletionRates() {
        try {
            List<Course> courses = courseRepository.findAll();
            List<User> users = userRepository.findAll();

            List<Map<String, Object>> rates = courses.stream()
                    .map(course -> {
                        Map<String, Object> rateMap = new HashMap<>();
                        rateMap.put("courseName", course.getCourseName());

                        // Count activities in this course
                        long totalActivities = course.getActivities() != null ?
                                course.getActivities().size() : 0;

                        if (totalActivities > 0 && users.size() > 0) {
                            // Calculate completion rate: (actual / possible) * 100
                            long totalPossibleCompletions = totalActivities * users.size();
                            long actualCompletions = activityCompletionRepository.findAll().stream()
                                    .filter(ac -> ac.getCourse().getCourseId().equals(course.getCourseId()))
                                    .count();

                            double rate = (double) actualCompletions / totalPossibleCompletions * 100;
                            rateMap.put("completionRate", Math.round(rate * 100.0) / 100.0);
                        } else {
                            rateMap.put("completionRate", 0.0);
                        }

                        return rateMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(rates);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ===========================================================================
    // TREND ANALYSIS
    // ===========================================================================

    // GET /api/analytics/activity-trends - Get activity completions for last 7 days
    @GetMapping("/activity-trends")
    public ResponseEntity<?> getActivityTrends() {
        try {
            List<Map<String, Object>> trends = new ArrayList<>();

            // Loop through last 7 days
            for (int i = 6; i >= 0; i--) {
                LocalDate date = LocalDate.now().minusDays(i);

                // Count completions for this date
                long count = activityCompletionRepository.findAll().stream()
                        .filter(ac -> ac.getCompletedAt() != null &&
                                ac.getCompletedAt().toLocalDate().equals(date))
                        .count();

                Map<String, Object> dayData = new HashMap<>();
                dayData.put("date", date.toString());
                dayData.put("count", count);
                trends.add(dayData);
            }

            return ResponseEntity.ok(trends);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // GET /api/analytics/user-growth - Get user registrations for last 30 days
    @GetMapping("/user-growth")
    public ResponseEntity<?> getUserGrowth() {
        try {
            List<Map<String, Object>> growth = new ArrayList<>();

            // Loop through last 30 days
            for (int i = 29; i >= 0; i--) {
                LocalDate date = LocalDate.now().minusDays(i);
                LocalDateTime startOfDay = date.atStartOfDay();
                LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

                // Count users created on this date
                long count = userRepository.findAll().stream()
                        .filter(u -> u.getCreatedAt() != null &&
                                u.getCreatedAt().isAfter(startOfDay) &&
                                u.getCreatedAt().isBefore(endOfDay))
                        .count();

                Map<String, Object> dayData = new HashMap<>();
                dayData.put("date", date.toString());
                dayData.put("count", count);
                growth.add(dayData);
            }

            return ResponseEntity.ok(growth);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}