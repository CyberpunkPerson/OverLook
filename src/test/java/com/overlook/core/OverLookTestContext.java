package com.overlook.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@TestConfiguration
public class OverLookTestContext {

    @Bean
    public MultipartFile correctData(@Value("${overlook.test.data.correct.name}") String name,
                                     @Value("${overlook.test.data.correct.contentType}") MediaType contentType,
                                     @Value("${overlook.test.data.correct.path}") String path) {
        return buildMultipartFile(name, contentType.toString(), path);
    }

    @Bean
    public MultipartFile wrongData(@Value("${overlook.test.data.wrong.name}") String name,
                                   @Value("${overlook.test.data.wrong.contentType}") MediaType contentType,
                                   @Value("${overlook.test.data.wrong.path}") String path) {
        return buildMultipartFile(name, contentType.toString(), path);
    }

    private MockMultipartFile buildMultipartFile(String name, String contentType, String path) {

        try {
            return new MockMultipartFile(name, name, contentType, new ClassPathResource(path).getInputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("MultipartFile with name: '%s' creation failed: %s",
                    name, e.getMessage()));
        }
    }
}
