package com.boredgame.controller;

import com.boredgame.entity.Event;
import com.boredgame.repos.JoiningRepo;
import com.boredgame.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FindController {

    private final EventService eventService;
    private final JoiningRepo joiningRepo;

    public FindController(EventService eventService, JoiningRepo joiningRepo) {
        this.eventService = eventService;
        this.joiningRepo = joiningRepo;
    }

    @GetMapping("/find")
    public String findPage(Model model, HttpSession session) {
        List<Event> events = eventService.getOpenEvents();
        Integer userId = (Integer) session.getAttribute("userId");

        Map<Integer, Integer> currentPlayers = new HashMap<>();
        Map<Integer, Boolean> userJoined = new HashMap<>();
        Map<Integer, Boolean> isFull = new HashMap<>();

        for (Event event : events) {
            int joined = joiningRepo.countByEventId(event.getId()) + 1; // +1 organizator
            currentPlayers.put(event.getId(), joined);
            isFull.put(event.getId(), joined >= event.getMaxPlayers());
            userJoined.put(event.getId(),
                    userId != null && joiningRepo.existsByEventIdAndUserId(event.getId(), userId));
        }

        model.addAttribute("events", events);
        model.addAttribute("currentPlayers", currentPlayers);
        model.addAttribute("userJoined", userJoined);
        model.addAttribute("isFull", isFull);
        model.addAttribute("loggedIn", userId != null);
        model.addAttribute("username", session.getAttribute("username"));

        return "find";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/find";
    }
}