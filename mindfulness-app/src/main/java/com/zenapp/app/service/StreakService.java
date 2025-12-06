package com.zenapp.app.service;

import com.zenapp.app.model.User;
import com.zenapp.app.model.UserStreak;
import com.zenapp.app.model.Badge;
import com.zenapp.app.model.UserBadge;
import com.zenapp.app.repository.UserStreakRepository;
import com.zenapp.app.repository.UserRepository;
import com.zenapp.app.repository.BadgeRepository;
import com.zenapp.app.repository.UserBadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for user activity streak management
 * Handles streak tracking, validation, leaderboards, and streak-based badges
 */
@Service
public class StreakService {

    @Autowired
    private UserStreakRepository streakRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private UserBadgeRepository userBadgeRepository;

    // ===========================================================================
    // STREAK UPDATES
    // ===========================================================================

    // Update user's streak after activity completion
    public UserStreak updateStreak(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<UserStreak> existingStreak = streakRepository.findByUser_UserId(userId);
        UserStreak streak;

        if (existingStreak.isEmpty()) {
            // First activity ever - initialize streak at 1
            streak = new UserStreak(user);
            streak.setCurrentStreak(1);
            streak.setLongestStreak(1);
            streak.setLastActivityDate(LocalDate.now());
        } else {
            streak = existingStreak.get();
            LocalDate today = LocalDate.now();
            LocalDate lastActivity = streak.getLastActivityDate();

            if (lastActivity == null) {
                // Streak exists but no date recorded
                streak.setCurrentStreak(1);
                streak.setLongestStreak(1);
                streak.setLastActivityDate(today);
            } else if (lastActivity.equals(today)) {
                // Activity already completed today - no change
                return streak;
            } else if (lastActivity.equals(today.minusDays(1))) {
                // Consecutive day - increment streak
                streak.setCurrentStreak(streak.getCurrentStreak() + 1);
                streak.setLastActivityDate(today);

                // Update longest streak if current exceeds it
                if (streak.getCurrentStreak() > streak.getLongestStreak()) {
                    streak.setLongestStreak(streak.getCurrentStreak());
                }
            } else {
                // Streak broken (gap > 1 day) - reset to 1
                streak.setCurrentStreak(1);
                streak.setLastActivityDate(today);
            }
        }

        streak = streakRepository.save(streak);

        // Check if user earned any streak badges
        checkAndAwardStreakBadges(user, streak.getCurrentStreak());

        return streak;
    }

    // ===========================================================================
    // STREAK RETRIEVAL
    // ===========================================================================

    // Get user's current streak (validates if still active)
    public UserStreak getUserStreak(Integer userId) {
        Optional<UserStreak> streak = streakRepository.findByUser_UserId(userId);

        if (streak.isEmpty()) {
            // No streak record exists - create new one
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            UserStreak newStreak = new UserStreak(user);
            return streakRepository.save(newStreak);
        }

        // Validate streak is still active
        UserStreak userStreak = streak.get();
        LocalDate today = LocalDate.now();
        LocalDate lastActivity = userStreak.getLastActivityDate();

        if (lastActivity != null && !lastActivity.equals(today) && !lastActivity.equals(today.minusDays(1))) {
            // Streak broken (more than 1 day gap) - reset to 0
            userStreak.setCurrentStreak(0);
            streakRepository.save(userStreak);
        }

        return userStreak;
    }

    // ===========================================================================
    // LEADERBOARDS
    // ===========================================================================

    // Get top 10 users by current streak
    public List<UserStreak> getTopCurrentStreaks() {
        return streakRepository.findTop10ByOrderByCurrentStreakDesc();
    }

    // Get top 10 users by longest streak ever achieved
    public List<UserStreak> getTopLongestStreaks() {
        return streakRepository.findTop10ByOrderByLongestStreakDesc();
    }

    // ===========================================================================
    // BADGE SYSTEM
    // ===========================================================================

    // Award streak-based badges if requirements met
    private void checkAndAwardStreakBadges(User user, Integer currentStreak) {
        // Get all streak badges (e.g., "7-day streak", "30-day streak")
        List<Badge> streakBadges = badgeRepository.findByRequirementType("streak");

        for (Badge badge : streakBadges) {
            if (currentStreak >= badge.getRequirementValue()) {
                // User qualifies for this badge
                boolean alreadyHas = userBadgeRepository
                        .existsByUser_UserIdAndBadge_BadgeId(user.getUserId(), badge.getBadgeId());

                if (!alreadyHas) {
                    // Award the badge
                    UserBadge userBadge = new UserBadge();
                    userBadge.setUser(user);
                    userBadge.setBadge(badge);
                    userBadge.setEarnedAt(LocalDateTime.now());
                    userBadgeRepository.save(userBadge);
                }
            }
        }
    }

    // ===========================================================================
    // ACTIVITY CHECKING
    // ===========================================================================

    // Check if user has completed an activity today
    public boolean hasActivityToday(Integer userId) {
        Optional<UserStreak> streak = streakRepository.findByUser_UserId(userId);
        if (streak.isEmpty()) {
            return false;
        }

        LocalDate today = LocalDate.now();
        return streak.get().getLastActivityDate() != null &&
                streak.get().getLastActivityDate().equals(today);
    }
}