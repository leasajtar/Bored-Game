package com.boredgame.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class AboutController {

    @GetMapping("/aboutus")
    public String aboutPage(HttpSession session, Model model) {

        model.addAttribute("loggedIn", session.getAttribute("userId") != null);
        model.addAttribute("username", session.getAttribute("username"));

        return "aboutus";
    }
}