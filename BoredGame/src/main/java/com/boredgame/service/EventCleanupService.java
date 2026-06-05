package com.boredgame.service;

import com.boredgame.entity.Event;
import com.boredgame.repos.EventRepo;
import com.boredgame.repos.JoiningRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventCleanupService{

    private final EventRepo eventRepo;
    private final JoiningRepo joiningRepo;

    public EventCleanupService(EventRepo eventRepo, JoiningRepo joiningRepo) {
        this.eventRepo = eventRepo;
        this.joiningRepo = joiningRepo;
    }

    @Transactional
    public void deleteExpiredEvents() {

        List<Event> expired =
                eventRepo.findByEventDateTimeBefore(LocalDateTime.now());

        if (expired.isEmpty()) return;

        // prvo obriši povezane joininge
        joiningRepo.deleteAllByEventIn(expired);

        // onda evente
        eventRepo.deleteAll(expired);
    }
}