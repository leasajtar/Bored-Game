package com.boredgame.controller;

import com.boredgame.entity.User;
import com.boredgame.repos.UsersRepos;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class AboutController {

    private final UsersRepos usersRepos;

    public AboutController(UsersRepos usersRepos) {
        this.usersRepos = usersRepos;
    }

    @GetMapping("/aboutus")
    public String aboutPage(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        model.addAttribute("loggedIn", userId != null);
        model.addAttribute("username", session.getAttribute("username"));

        if (userId != null) {
            User user = usersRepos.findById(userId).orElse(null);
            if (user != null) {
                model.addAttribute("profilePicture", user.getProfilePicture());
            }
        }

        return "aboutus";
    }
}