package com.overlook.security.controller;

import com.overlook.security.config.JwtTokenProvider;
import com.overlook.security.domain.AuthenticationRequest;
import com.overlook.security.domain.AuthenticationResponse;
import com.overlook.security.domain.UserProfile;
import com.overlook.security.exception.InvalidTokenException;
import com.overlook.security.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

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

        return ResponseEntity.ok(new AuthenticationResponse(jwt, userProfile));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {

        Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .ifPresent(authentication -> new SecurityContextLogoutHandler().logout(request, response, authentication));

        return ResponseEntity.ok("Log out successful");
    }

    @PostMapping("/check") //TODO refactor this approach
    public ResponseEntity<AuthenticationResponse> check() {

        Authentication authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .orElseThrow(() -> new InvalidTokenException("Failed to restore token"));

        String jwt = tokenProvider.generateToken(authentication);
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();

        return ResponseEntity.ok(new AuthenticationResponse(jwt, userProfile));
    }
}
