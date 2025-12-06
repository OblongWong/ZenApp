package com.zenapp.app.service;

import com.zenapp.app.model.Badge;
import com.zenapp.app.model.UserBadge;
import com.zenapp.app.repository.BadgeRepository;
import com.zenapp.app.repository.UserBadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for badge management
 * Handles badge retrieval, user badge tracking, and admin operations
 */
@Service
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private UserBadgeRepository userBadgeRepository;

    // ===========================================================================
    // BADGE RETRIEVAL
    // ===========================================================================

    // Get all available badges in the system
    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    // Get specific badge by ID
    public Badge getBadgeById(Integer badgeId) {
        return badgeRepository.findById(badgeId)
                .orElseThrow(() -> new RuntimeException("Badge not found!"));
    }

    // ===========================================================================
    // USER BADGE OPERATIONS
    // ===========================================================================

    // Get all badges earned by a specific user
    public List<UserBadge> getUserBadges(Integer userId) {
        return userBadgeRepository.findByUser_UserId(userId);
    }

    // Count total badges earned by user
    public long countUserBadges(Integer userId) {
        return userBadgeRepository.countByUser_UserId(userId);
    }

    // Check if user has earned a specific badge
    public boolean userHasBadge(Integer userId, Integer badgeId) {
        return userBadgeRepository.existsByUser_UserIdAndBadge_BadgeId(userId, badgeId);
    }

    // ===========================================================================
    // ADMIN OPERATIONS
    // ===========================================================================

    // Create or update a badge
    public Badge saveBadge(Badge badge) {
        return badgeRepository.save(badge);
    }

    // Delete a badge from the system
    public void deleteBadge(Integer badgeId) {
        badgeRepository.deleteById(badgeId);
    }
}