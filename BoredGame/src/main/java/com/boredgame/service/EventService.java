package com.boredgame.service;

import com.boredgame.entity.Event;
import com.boredgame.repos.EventRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {

    private final EventRepo eventRepo;

    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    // Vraća samo OPEN evente
    public List<Event> getOpenEvents() {
        return eventRepo.findByStatus("OPEN");
    }
}