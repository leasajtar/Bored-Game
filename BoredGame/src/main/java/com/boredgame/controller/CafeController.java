package com.boredgame.controller;

import com.boredgame.repos.CafeRepos;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CafeController {

    private final CafeRepos cafeRepository;

    public CafeController(CafeRepos cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    @GetMapping("/organiziranje")
    public String showPage(Model model) {
        model.addAttribute("cafes", cafeRepository.findAll());
        return "organiziranje"; // templates/organiziranje.html
    }
}