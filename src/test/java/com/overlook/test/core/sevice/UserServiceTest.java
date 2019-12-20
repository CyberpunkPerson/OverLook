package com.overlook.test.core.sevice;

import com.overlook.core.domain.provider.PhoneNumber;
import com.overlook.core.domain.provider.Provider;
import com.overlook.core.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Test
    public void changeProviderWhenBalanceHigherSuccessful() {
        Provider originalProvider = Provider.builder()
                .providerId(UUID.randomUUID())
                .build();

        PhoneNumber phoneNumber = PhoneNumber.builder()
                .balance(new BigDecimal(101))
                .provider(originalProvider)
                .build();

        Provider newProvider = Provider.builder()
                .providerId(UUID.randomUUID())
                .build();


        userService.changeProvider(phoneNumber, newProvider);

        assertEquals(phoneNumber.getProvider().getProviderId(), newProvider.getProviderId());
    }

    @Test
    public void changeProviderWhenBalanceEqualSuccessful() {
        Provider originalProvider = Provider.builder()
                .providerId(UUID.randomUUID())
                .build();

        PhoneNumber phoneNumber = PhoneNumber.builder()
                .balance(new BigDecimal(100))
                .provider(originalProvider)
                .build();

        Provider newProvider = Provider.builder()
                .providerId(UUID.randomUUID())
                .build();

        userService.changeProvider(phoneNumber, newProvider);

        assertEquals(phoneNumber.getProvider().getProviderId(), newProvider.getProviderId());
    }

    @Test
    public void changeProviderWhenBalanceLowerExceptionThrown() {
        Provider originalProvider = Provider.builder()
                .providerId(UUID.randomUUID())
                .build();

        PhoneNumber phoneNumber = PhoneNumber.builder()
                .balance(new BigDecimal(99))
                .provider(originalProvider)
                .build();

        Provider newProvider = Provider.builder()
                .providerId(UUID.randomUUID())
                .build();


        assertThrows(IllegalArgumentException.class, () -> userService.changeProvider(phoneNumber, newProvider));
    }
}
