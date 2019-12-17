package com.overlook.security.repository;

import com.overlook.security.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    Optional<UserProfile> findByUsername(String username);

    Optional<UserProfile> findByUserId(UUID userId);
}
