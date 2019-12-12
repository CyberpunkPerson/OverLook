package com.overlook.core.repository;

import com.overlook.core.domain.provider.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {
}
