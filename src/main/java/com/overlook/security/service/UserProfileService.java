package com.overlook.security.service;

import com.overlook.security.domain.AuthenticationRequest;
import com.overlook.security.domain.UserProfile;
import com.overlook.security.repository.UserProfileRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

@Service
public class UserProfileService implements UserDetailsService {

    private final UserProfileRepository userProfileRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository, AuthenticationManager authenticationManager) {
        this.userProfileRepository = userProfileRepository;
        this.authenticationManager = authenticationManager;
    }

    //TODO to think about validation
    public Authentication authenticate(AuthenticationRequest authenticationRequest) throws AuthenticationException {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        Assert.isTrue(!StringUtils.isBlank(username), "Username should be defined");
        Assert.isTrue(!StringUtils.isBlank(password), "Password should be defined");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    @Override
    public UserProfile loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.isTrue(!StringUtils.isBlank(username), "Username should be defined");

        UserProfile userProfile = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with name '%s' was not found", username)
                ));

        if (!userProfile.isAccountNonExpired() || !userProfile.isAccountNonLocked()) {
            throw new AccountExpiredException("This account doesn't not active");
        }

        return userProfile;
    }

    public UserDetails loadUserById(UUID userId) {
        Assert.notNull(userId, "Undefined user id has been passed");
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with id: '%s' was not found", userId)
                ));
    }

    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }
}
