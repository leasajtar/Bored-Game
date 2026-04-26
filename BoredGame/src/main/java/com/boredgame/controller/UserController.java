package com.boredgame.controller;

import com.boredgame.entity.User;
import com.boredgame.repos.UsersRepos;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UsersRepos usersRepos;

    public UserController(UsersRepos usersRepos) {
        this.usersRepos = usersRepos;
    }

    // ---- REGISTRACIJA ----

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String ime,
                           @RequestParam String prezime,
                           @RequestParam(defaultValue = "0") int gender,
                           @RequestParam(required = false) String phone) {

        if (usersRepos.findByUsername(username) != null) {
            return "redirect:/register?error=exists";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setIme(ime);
        user.setPrezime(prezime);
        user.setGender(gender);
        user.setPhone(phone != null ? phone : "");

        usersRepos.save(user);
        return "redirect:/login?success";
    }

    // ---- LOGIN ----

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {

        User user = usersRepos.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            return "redirect:/find";
        } else {
            return "redirect:/login?error";
        }
    }

    // ---- LOGOUT ----

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/find";
    }
}
