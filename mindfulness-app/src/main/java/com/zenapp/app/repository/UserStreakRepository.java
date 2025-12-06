package com.zenapp.app.repository;

import com.zenapp.app.model.UserStreak;
import com.zenapp.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * Repository interface for UserStreak entity
 * Provides database access methods for user activity streaks
 * Spring Data JPA auto-generates implementation from method names
 */
@Repository
public interface UserStreakRepository extends JpaRepository<UserStreak, Integer> {

    // Find streak record for a user (using User object)
    Optional<UserStreak> findByUser(User user);

    // Find streak record by user ID
    Optional<UserStreak> findByUser_UserId(Integer userId);

    // Check if user has a streak record (returns true/false)
    boolean existsByUser_UserId(Integer userId);

    // Get top 10 users by current streak (for leaderboard)
    List<UserStreak> findTop10ByOrderByCurrentStreakDesc();

    // Get top 10 users by longest streak ever (for all-time leaderboard)
    List<UserStreak> findTop10ByOrderByLongestStreakDesc();
}