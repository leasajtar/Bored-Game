package com.boredgame.controller;

import com.boredgame.entity.Event;
import com.boredgame.entity.EventDTO;
import com.boredgame.repos.EventRepo;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/organiziranje.html")
@CrossOrigin(origins = "http://localhost:8080")
public class EventController {

    private final EventRepo eventRepository;

    public EventController(EventRepo eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/events")
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

    @PostMapping("/events")
    public String createEvent(@RequestBody Event event) {
        System.out.println("Received event: " + event);
        eventRepository.save(event);
        return "Event saved!";
    }
}