package com.boredgame.controller;

import com.boredgame.entity.Event;
import com.boredgame.entity.Joining;
import com.boredgame.repos.JoiningRepo;
import com.boredgame.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class FindController {

    private final EventService eventService;
    private final JoiningRepo joiningRepo;

    public FindController(EventService eventService, JoiningRepo joiningRepo) {
        this.eventService = eventService;
        this.joiningRepo = joiningRepo;
    }

    private String getGameType(String gameName) {
        if (gameName == null) return "Ostalo";

        return switch (gameName) {
            case "Alias", "Scrabble" -> "Riječi";
            case "Uno", "Briškule", "Poker", "Exploding Kittens" -> "Karte";
            case "Catan", "Risk", "Monopoly", "Šah" -> "Strategije";
            case "Vukodlak" -> "Dedukcija";
            default -> "Ostalo";
        };
    }

    @GetMapping("/find")
    public String findPage(Model model, HttpSession session) {
        List<Event> events = eventService.getOpenEvents();
        Integer userId = (Integer) session.getAttribute("userId");

        Map<Integer, Integer> currentPlayers = new HashMap<>();
        Map<Integer, Boolean> userJoined = new HashMap<>();
        Map<Integer, Boolean> isFull = new HashMap<>();
        Map<Integer, Boolean> isOrganizer = new HashMap<>();
        Map<Integer, String> gameTypes = new HashMap<>();
        Map<Integer, List<String>> eventParticipants = new HashMap<>();

        for (Event event : events) {
            int joined = joiningRepo.countByEventId(event.getId());
            currentPlayers.put(event.getId(), joined);

            Integer maxPlayers = event.getMaxPlayers();
            if (maxPlayers == null) maxPlayers = 4;

            isFull.put(event.getId(), joined >= maxPlayers);

            userJoined.put(event.getId(),
                    userId != null && joiningRepo.existsByEventIdAndUserId(event.getId(), userId));

            isOrganizer.put(event.getId(),
                    userId != null &&
                            event.getOrganizator() != null &&
                            event.getOrganizator().getId() == userId);

            gameTypes.put(event.getId(), getGameType(event.getNazivIgre()));

            // Dohvati listu usernamea prijavljenih igrača
            List<String> participants = joiningRepo.findByEventId(event.getId())
                    .stream()
                    .map(j -> j.getUser().getUsername())
                    .collect(Collectors.toList());
            eventParticipants.put(event.getId(), participants);
        }

        model.addAttribute("gameTypes", gameTypes);
        model.addAttribute("events", events);
        model.addAttribute("currentPlayers", currentPlayers);
        model.addAttribute("userJoined", userJoined);
        model.addAttribute("isFull", isFull);
        model.addAttribute("isOrganizer", isOrganizer);
        model.addAttribute("eventParticipants", eventParticipants);
        model.addAttribute("loggedIn", userId != null);
        model.addAttribute("username", session.getAttribute("username"));

        return "find";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/find";
    }
}