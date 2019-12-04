package com.overlook.core.controller;

import com.overlook.core.domain.user.User;
import com.overlook.core.service.DirectoryService;
import com.overlook.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/directory")
public class DirectoryController {

    private final UserService userService;

    private final DirectoryService directoryService;

    @Autowired
    public DirectoryController(UserService userService, DirectoryService directoryService) {
        this.userService = userService;
        this.directoryService = directoryService;
    }


    @PostMapping("/save")
    public ResponseEntity saveDirectory(@RequestBody MultipartFile directory) {
        List<User> extractedUsers = directoryService.extractUsers(directory);
        userService.saveAll(extractedUsers);
        return ResponseEntity.ok("Data has been saved");
    }

}
