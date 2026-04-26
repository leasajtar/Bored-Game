package com.boredgame.controller;

import com.boredgame.entity.*;
import com.boredgame.repos.CafeRepos;
import com.boredgame.repos.EventRepo;
import com.boredgame.repos.UsersRepos;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class EventController {

    private final EventRepo eventRepository;
    private final CafeRepos CafeRepos;
    private final UsersRepos usersRepos;

    public EventController(EventRepo eventRepository, CafeRepos CafeRepos, UsersRepos usersRepos) {
        this.eventRepository = eventRepository;
        this.CafeRepos = CafeRepos;
        this.usersRepos = usersRepos;
    }

    @GetMapping("/organiziranje/events")
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> dtoList = new ArrayList<>();

        for (Event event : events) {
            EventDTO dto = new EventDTO();
            dto.setId(event.getId());
            dto.setGame_name(event.getNazivIgre());
            dto.setCafe_name(event.getKafic().getName());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            dto.setEvent_datetime(event.getDatumVrijeme().format(formatter));

            dto.setMax_players(event.getMaxPlayers());

            int currentPlayers = event.getPridruzivanja().size() + 1;
            dto.setCurrentPlayers(currentPlayers);
            dto.setFreeSpaces(event.getMaxPlayers() - currentPlayers);
            dto.setUserAccepted(false);
            dto.setOrganizer(false);

            dtoList.add(dto);
        }

        return dtoList;
    }

    @PostMapping("/organiziranje/events")
    public ResponseEntity<String> createEvent(
            @RequestBody EventRequest request,   // ← was @RequestParam, now @RequestBody
            HttpSession session
    ) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        if (request.getGame_name() == null || request.getGame_name().isBlank()) {
            return ResponseEntity.badRequest().body("No game selected");
        }

        Optional<Cafe> cafeOpt = CafeRepos.findById(request.getCafe_id());
        if (cafeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Cafe not found");
        }

        Optional<User> userOpt = usersRepos.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Event event = new Event();
        event.setGameName(request.getGame_name());
        event.setMaxPlayers(request.getMax_players());
        event.setCafe(cafeOpt.get());
        event.setOrganizer(userOpt.get());
        event.setLevel(request.getLevel());
        event.setEventDatetime(LocalDateTime.now());
        event.setStatus("open");

        eventRepository.save(event);
        return ResponseEntity.ok("Event saved!");
    }
}