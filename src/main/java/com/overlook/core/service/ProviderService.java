package com.overlook.core.service;

import com.overlook.core.domain.provider.Provider;
import com.overlook.core.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public Provider saveProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public List<Provider> findAll() {
        return providerRepository.findAll();
    }
}
