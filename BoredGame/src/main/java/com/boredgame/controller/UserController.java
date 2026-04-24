package com.boredgame.controller;

import com.boredgame.entity.Users;
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

    @PostMapping("/users")
    public String saveUser(@RequestBody Users user) {
        System.out.println("Received user: " + user);
        userRepository.save(user);
        return "User saved!";
    }
}