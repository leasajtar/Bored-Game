package com.boredgame.controller;

import com.boredgame.entity.Event;
import com.boredgame.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FindController {

    private final EventService eventService;

    public FindController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/find")
    public String findPage(Model model) {
        List<Event> events = eventService.getOpenEvents();
        System.out.println("EVENTI: " + events.size());

        model.addAttribute("events", events);
        return "find";
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/find";
    }
}