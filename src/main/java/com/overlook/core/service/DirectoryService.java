package com.overlook.core.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.overlook.core.domain.user.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DirectoryService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> extractUsers(MultipartFile directory) {
        Assert.notNull(directory, "Undefined file was passed for extraction");
        Assert.notNull(directory.getContentType(), "Content type not specified");
        Assert.isTrue(directory.getContentType().equals(MediaType.APPLICATION_JSON_VALUE), "JSON format applicable only");

        try {
            return objectMapper.readValue(directory.getInputStream(), new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(String.format("Data extraction failed: %s", e.getMessage()));
        }
    }
}
