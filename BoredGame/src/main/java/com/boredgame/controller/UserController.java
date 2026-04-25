package com.boredgame.controller;

import com.boredgame.entity.User;
import com.boredgame.repos.UsersRepos;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    private final UsersRepos userRepository;

    public UserController(UsersRepos userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String saveUser(@RequestBody User user) {
        System.out.println("Received user: " + user);
        userRepository.save(user);
        return "User saved!";
    }
}