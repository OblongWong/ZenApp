package com.zenapp.app.repository;

import com.zenapp.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity
 * Provides database access methods for user accounts
 * Spring Data JPA auto-generates implementation from method names
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find user by username (for login)
    Optional<User> findByUsername(String username);

    // Find user by email (for login or password recovery)
    Optional<User> findByEmail(String email);

    // Check if username is already taken (for registration validation)
    boolean existsByUsername(String username);

    // Check if email is already taken (for registration validation)
    boolean existsByEmail(String email);
}