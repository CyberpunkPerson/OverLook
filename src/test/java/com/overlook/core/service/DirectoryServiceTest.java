package com.overlook.core.service;

import com.overlook.core.OverLookTestContext;
import com.overlook.core.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OverLookTestContext.class)
public class DirectoryServiceTest {

    @Resource(name = "correctData")
    private MultipartFile correctData;

    @Resource(name = "wrongData")
    private MultipartFile wrongData;

    @InjectMocks
    private DirectoryService directoryService;

    @Test
    public void parseSuccessfulWithCorrectData() {
        List<User> extractedUsers = directoryService.extractUsers(correctData);

        assertEquals(2, extractedUsers.size());
    }

    @Test
    public void parseWrongFormatDataExceptionThrown() {
        assertThrows(RuntimeException.class, () -> directoryService.extractUsers(wrongData));
    }

}
