package com.overlook.core.service;

import com.overlook.core.domain.user.User;
import com.overlook.core.repository.UserRepository;
import com.overlook.security.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasAuthority('BOOKING_MANAGER')")
    public User saveUser(User user) {
        encodeUserPassword(user);
        return userRepository.save(user);
    }

    @PreAuthorize("hasAuthority('BOOKING_MANAGER')")
    public List<User> saveAll(List<User> users) {
        users.forEach(this::encodeUserPassword);

        return userRepository.saveAll(users);
    }

    @PreAuthorize("hasAuthority('BOOKING_MANAGER')")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //FIXME Kludge, refactor this
    private void encodeUserPassword(User user) {
        UserProfile profile = user.getProfile();
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
    }
}
