package com.overlook.core.controller;

import com.overlook.core.domain.user.User;
import com.overlook.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody @Valid User user) {
        userService.saveUser(user);
        return ResponseEntity.ok("User has been saved");
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}
