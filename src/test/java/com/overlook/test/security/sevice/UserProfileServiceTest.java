package com.overlook.test.security.sevice;

import com.overlook.security.domain.AuthenticationRequest;
import com.overlook.security.repository.UserProfileRepository;
import com.overlook.security.service.UserProfileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
public class UserProfileServiceTest {

    @InjectMocks
    private UserProfileService userProfileService;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void authenticationWithWrongRequestThrowException() {

        AuthenticationRequest request = new AuthenticationRequest("", "");
        assertThrows(IllegalArgumentException.class, () -> userProfileService.authenticate(request));

    }
}
