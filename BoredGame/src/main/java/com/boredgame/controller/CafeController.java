package com.boredgame.controller;

import com.boredgame.entity.Cafe;
import com.boredgame.entity.User;
import com.boredgame.repos.CafeRepos;
import com.boredgame.repos.UsersRepos;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CafeController {

    private final CafeRepos cafeRepo;
    private final UsersRepos usersRepos;

    public CafeController(CafeRepos cafeRepo, UsersRepos usersRepos) {
        this.cafeRepo = cafeRepo;
        this.usersRepos = usersRepos;
    }

    @GetMapping("/organiziranje")
    public String showPage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        User user = usersRepos.findById(userId).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        List<Cafe> cafes = cafeRepo.findAll();

        model.addAttribute("cafes", cafes);
        model.addAttribute("loggedIn", true);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("profilePicture", user.getProfilePicture());

        return "organiziranje";
    }
}