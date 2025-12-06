package com.zenapp.app.service;

import com.zenapp.app.model.*;
import com.zenapp.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityCompletionService {

    @Autowired
    private ActivityCompletionRepository completionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private UserBadgeRepository userBadgeRepository;

    @Autowired
    private StreakService streakService;

    // Complete an activity and earn points
    public ActivityCompletion completeActivity(Integer userId, Integer activityId) {
        // Check if already completed
        if (completionRepository.existsByUser_UserIdAndActivity_ActivityId(userId, activityId)) {
            throw new RuntimeException("Activity already completed!");
        }

        // Get user and activity
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found!"));

        Course course = activity.getCourse();

        // Create completion record
        ActivityCompletion completion = new ActivityCompletion();
        completion.setUser(user);
        completion.setActivity(activity);
        completion.setCourse(course);
        completion.setPointsEarned(activity.getPointsReward());
        completion.setCompletedAt(LocalDateTime.now());

        // Save completion
        completionRepository.save(completion);

        // Add points to user
        user.setTotalPoints(user.getTotalPoints() + activity.getPointsReward());
        userRepository.save(user);

        // Update user's streak when completing activity
        streakService.updateStreak(userId);

        // Check for new badges
        checkAndAwardBadges(user);

        return completion;
    }

    // Get all completions for a user
    public List<ActivityCompletion> getUserCompletions(Integer userId) {
        return completionRepository.findByUser_UserId(userId);
    }

    // Check if user completed an activity
    public boolean hasUserCompletedActivity(Integer userId, Integer activityId) {
        return completionRepository.existsByUser_UserIdAndActivity_ActivityId(userId, activityId);
    }

    // Count completed activities for a user
    public long countUserCompletions(Integer userId) {
        return completionRepository.countByUser_UserId(userId);
    }

    // Get completions for a specific course
    public List<ActivityCompletion> getUserCourseCompletions(Integer userId, Integer courseId) {
        return completionRepository.findByUser_UserIdAndCourse_CourseId(userId, courseId);
    }

    // Calculate course progress percentage
    public double getCourseProgress(Integer userId, Integer courseId) {
        List<Activity> allActivities = activityRepository.findByCourse_CourseId(courseId);
        List<ActivityCompletion> completions = completionRepository
                .findByUser_UserIdAndCourse_CourseId(userId, courseId);

        if (allActivities.isEmpty()) {
            return 0.0;
        }

        return (double) completions.size() / allActivities.size() * 100;
    }

    // Check and award badges based on user progress
    private void checkAndAwardBadges(User user) {
        // Get user's stats
        long completedActivities = completionRepository.countByUser_UserId(user.getUserId());
        int totalPoints = user.getTotalPoints();

        // Check for activity-based badges
        List<Badge> activityBadges = badgeRepository
                .findByRequirementTypeAndRequirementValueLessThanEqual("activities", (int) completedActivities);

        for (Badge badge : activityBadges) {
            awardBadgeIfNotEarned(user, badge);
        }

        // Check for points-based badges
        List<Badge> pointsBadges = badgeRepository
                .findByRequirementTypeAndRequirementValueLessThanEqual("points", totalPoints);

        for (Badge badge : pointsBadges) {
            awardBadgeIfNotEarned(user, badge);
        }
    }

    // Award badge if user doesn't already have it
    private void awardBadgeIfNotEarned(User user, Badge badge) {
        boolean alreadyHas = userBadgeRepository
                .existsByUser_UserIdAndBadge_BadgeId(user.getUserId(), badge.getBadgeId());

        if (!alreadyHas) {
            UserBadge userBadge = new UserBadge();
            userBadge.setUser(user);
            userBadge.setBadge(badge);
            userBadge.setEarnedAt(LocalDateTime.now());
            userBadgeRepository.save(userBadge);
        }
    }
    // Undo/uncomplete a single activity
    public void uncompleteActivity(Integer userId, Integer activityId) {
        // Check if activity was completed
        if (!completionRepository.existsByUser_UserIdAndActivity_ActivityId(userId, activityId)) {
            throw new RuntimeException("Activity was not completed!");
        }

        // Find all completions for this user
        List<ActivityCompletion> allCompletions = completionRepository.findByUser_UserId(userId);

        // Find the specific one we want to delete
        ActivityCompletion toDelete = null;
        for (ActivityCompletion comp : allCompletions) {
            if (comp.getActivity().getActivityId().equals(activityId)) {
                toDelete = comp;
                break;
            }
        }

        if (toDelete == null) {
            throw new RuntimeException("Completion not found!");
        }

        Integer pointsToRemove = toDelete.getPointsEarned();

        // Delete the completion
        completionRepository.delete(toDelete);

        // Remove points from user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        user.setTotalPoints(Math.max(0, user.getTotalPoints() - pointsToRemove));
        userRepository.save(user);

        // Remove badges if no longer qualified
        removeUnqualifiedBadges(user);
    }

    // Reset entire course progress
    public void resetCourseProgress(Integer userId, Integer courseId) {
        // Find all completions for this course
        List<ActivityCompletion> completions = completionRepository
                .findByUser_UserIdAndCourse_CourseId(userId, courseId);

        if (completions.isEmpty()) {
            throw new RuntimeException("No progress found for this course!");
        }

        // Calculate total points to remove
        Integer totalPointsToRemove = 0;
        for (ActivityCompletion comp : completions) {
            totalPointsToRemove += comp.getPointsEarned();
        }

        // Delete all completions
        completionRepository.deleteAll(completions);

        // Remove points from user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        user.setTotalPoints(Math.max(0, user.getTotalPoints() - totalPointsToRemove));
        userRepository.save(user);

        // Remove badges if no longer qualified
        removeUnqualifiedBadges(user);
    }

    // Remove badges user no longer qualifies for
    private void removeUnqualifiedBadges(User user) {
        // Get user's current stats
        long completedActivities = completionRepository.countByUser_UserId(user.getUserId());
        int totalPoints = user.getTotalPoints();

        // Get all user's badges
        List<UserBadge> userBadges = userBadgeRepository.findByUser_UserId(user.getUserId());

        for (UserBadge userBadge : userBadges) {
            Badge badge = userBadge.getBadge();
            boolean shouldRemove = false;

            // Check if user still qualifies
            if ("activities".equals(badge.getRequirementType())) {
                if (completedActivities < badge.getRequirementValue()) {
                    shouldRemove = true;
                }
            } else if ("points".equals(badge.getRequirementType())) {
                if (totalPoints < badge.getRequirementValue()) {
                    shouldRemove = true;
                }
            }
            // Don't remove streak badges

            if (shouldRemove) {
                userBadgeRepository.delete(userBadge);
            }
        }
    }
}
