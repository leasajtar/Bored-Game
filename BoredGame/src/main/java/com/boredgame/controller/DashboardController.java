package com.boredgame.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(
            HttpSession session,
            Model model
    ) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("adminLoggedIn");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/admin/login";
        }

        model.addAttribute(
                "adminName",
                session.getAttribute("adminName")
        );

        return "dashboard";
    }
}