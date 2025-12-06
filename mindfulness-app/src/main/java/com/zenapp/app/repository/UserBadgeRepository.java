package com.zenapp.app.repository;

import com.zenapp.app.model.UserBadge;
import com.zenapp.app.model.User;
import com.zenapp.app.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for UserBadge entity
 * Provides database access methods for user-earned badges
 * Spring Data JPA auto-generates implementation from method names
 */
@Repository
public interface UserBadgeRepository extends JpaRepository<UserBadge, Integer> {

    // Find all badges earned by a user (using User object)
    List<UserBadge> findByUser(User user);

    // Find all badges earned by user ID
    List<UserBadge> findByUser_UserId(Integer userId);

    // Find specific user-badge record (using objects)
    Optional<UserBadge> findByUserAndBadge(User user, Badge badge);

    // Check if user already has a specific badge (returns true/false)
    boolean existsByUser_UserIdAndBadge_BadgeId(Integer userId, Integer badgeId);

    // Count total badges earned by user
    long countByUser_UserId(Integer userId);
}