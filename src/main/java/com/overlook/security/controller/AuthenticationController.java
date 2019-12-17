package com.overlook.security.controller;

import com.overlook.security.config.JwtTokenProvider;
import com.overlook.security.domain.AuthenticationRequest;
import com.overlook.security.domain.AuthenticationResponse;
import com.overlook.security.domain.UserProfile;
import com.overlook.security.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserProfileService userProfileService;

    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthenticationController(UserProfileService userProfileService, JwtTokenProvider tokenProvider) {
        this.userProfileService = userProfileService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {

        Authentication authentication = userProfileService.authenticate(authenticationRequest);
        String jwt = tokenProvider.generateToken(authentication);

        UserProfile userProfile = userProfileService.loadUserByUsername(authenticationRequest.getUsername());
        List<String> roles = userProfile.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return ResponseEntity.ok(new AuthenticationResponse(userProfile.getUserId(), jwt, userProfile.getUsername(), roles));
    }
}
