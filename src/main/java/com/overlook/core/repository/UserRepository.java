package com.overlook.core.repository;

import com.overlook.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByProfileProfileId(UUID profileId);
}
