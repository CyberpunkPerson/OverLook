package com.overlook.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.overlook.core.converter.OverlookMultipartFile;
import com.overlook.core.domain.user.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DirectoryService {

    private static final String DEFAULT_FILENAME = "overlook-dictionary";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> extractUsers(MultipartFile directory) {
        Assert.notNull(directory, "Undefined file was passed for extraction");

        try {
            return objectMapper.readValue(directory.getInputStream(), new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(String.format("Data extraction failed: %s", e.getMessage()));
        }
    }

    public MultipartFile writeUsers(List<User> users) {
        Assert.notNull(users, "Undefined list of users passed for write");

        try {
            return new OverlookMultipartFile(DEFAULT_FILENAME, MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(users));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Write data failed: %s", e.getMessage()));
        }
    }
}
