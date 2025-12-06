package com.zenapp.app.repository;

import com.zenapp.app.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Badge entity
 * Provides database access methods for achievement badges
 * Spring Data JPA auto-generates implementation from method names
 */
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {

    // Find all badges of a specific type (e.g., "points", "streak", "activities")
    List<Badge> findByRequirementType(String requirementType);

    // Find badges that user qualifies for (requirement met or exceeded)
    List<Badge> findByRequirementTypeAndRequirementValueLessThanEqual(
            String requirementType, Integer value);
}