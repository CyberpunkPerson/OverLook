package com.overlook.core.controller;

import com.overlook.core.domain.provider.Provider;
import com.overlook.core.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody @Valid Provider provider) {
        providerService.saveProvider(provider);
        return ResponseEntity.ok("User has been saved");
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(providerService.findAll());
    }
}
