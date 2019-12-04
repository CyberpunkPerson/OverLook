package com.overlook.core.service;

import com.overlook.core.domain.company.Provider;
import com.overlook.core.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public Provider save(Provider provider) {
        return providerRepository.save(provider);
    }
}
