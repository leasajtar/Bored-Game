package com.boredgame.controller;

import com.boredgame.entity.Cafe;
import com.boredgame.repos.CafeRepos;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CafeController {

    private final CafeRepos cafeRepo;

    public CafeController(CafeRepos cafeRepo) {
        this.cafeRepo = cafeRepo;
    }

    @GetMapping("/organiziranje")
    public String showPage(Model model, HttpSession session) {  // add HttpSession here
        Integer userId = (Integer) session.getAttribute("userId");

        // Not logged in → redirect to login
        if (userId == null) {
            return "redirect:/login";
        }

        List<Cafe> cafes = cafeRepo.findAll();
        model.addAttribute("cafes", cafes);
        model.addAttribute("loggedIn", true);
        model.addAttribute("username", session.getAttribute("username"));

        return "organiziranje";
    }
}