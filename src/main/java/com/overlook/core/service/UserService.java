package com.overlook.core.service;

import com.overlook.core.domain.provider.PhoneNumber;
import com.overlook.core.domain.provider.Provider;
import com.overlook.core.domain.user.ContactInfo;
import com.overlook.core.domain.user.User;
import com.overlook.core.repository.UserRepository;
import com.overlook.security.domain.ProfileRole;
import com.overlook.security.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collections;
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

   /**
    * Init user, in development purpose only
   * */
    @PostConstruct
    public void init() {
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername("admin");
        userProfile.setPassword(passwordEncoder.encode("12345678"));
        userProfile.setAccountNonExpired(true);
        userProfile.setAccountNonLocked(true);
        userProfile.setAuthorities(ProfileRole.BOOKING_MANAGER);
        userProfile.setEnabled(true);
        userProfile.setCredentialsNonExpired(true);

        Provider provider = new Provider();
        provider.setName("Provider");

        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhoneNumber("64345243");
        phoneNumber.setProvider(provider);

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail("123523");
        contactInfo.setPhoneNumbers(Collections.singletonList(phoneNumber));

        User user = new User();
        user.setProfile(userProfile);
        user.setName("Admin");
        user.setContactInfo(contactInfo);

        userRepository.save(user);
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

    @PreAuthorize("hasAuthority('BOOKING_MANAGER') or hasAuthority('REGISTERED_USER')")
    public User retrieveActiveUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserProfile userProfile = (UserProfile) authentication.getPrincipal();

        return userRepository.findByProfileProfileId(userProfile.getProfileId())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Profile with %s - id does not exist", userProfile.getProfileId())));
    }

    @PreAuthorize("hasAuthority('BOOKING_MANAGER')")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void  changeProvider(PhoneNumber phoneNumber, Provider provider) {

        Assert.isTrue(phoneNumber.getBalance().compareTo(new BigDecimal(100)) >= 0, "Not enough money to change provider");

        phoneNumber.setProvider(provider);
    }

    //FIXME Kludge, refactor this
    private void encodeUserPassword(User user) {
        UserProfile profile = user.getProfile();
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
    }
}
