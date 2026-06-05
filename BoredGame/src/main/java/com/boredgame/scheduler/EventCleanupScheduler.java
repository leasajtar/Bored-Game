package com.boredgame.scheduler;

import com.boredgame.service.EventCleanupService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventCleanupScheduler {

    private final EventCleanupService eventCleanupService;

    public EventCleanupScheduler(EventCleanupService eventCleanupService) {
        this.eventCleanupService = eventCleanupService;
    }

    @Scheduled(cron = "0 1 0 * * *", zone = "Europe/Zagreb")
    public void runCleanup() {
        System.out.println("Scheduler pokrenut");
        eventCleanupService.deleteExpiredEvents();
    }
}