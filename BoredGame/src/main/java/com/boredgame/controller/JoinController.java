package com.boredgame.controller;

import com.boredgame.entity.Event;
import com.boredgame.entity.Joining;
import com.boredgame.entity.User;
import com.boredgame.repos.EventRepo;
import com.boredgame.repos.JoiningRepo;
import com.boredgame.repos.UsersRepos;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class JoinController {

    private final EventRepo eventRepo;
    private final JoiningRepo joiningRepo;
    private final UsersRepos usersRepos;

    public JoinController(EventRepo eventRepo, JoiningRepo joiningRepo, UsersRepos usersRepos) {
        this.eventRepo = eventRepo;
        this.joiningRepo = joiningRepo;
        this.usersRepos = usersRepos;
    }

    @PostMapping("/join")
    public String join(@RequestParam Integer eventId, HttpSession session) {

        // login provjera
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<Event> eventOpt = eventRepo.findById(eventId);
        Optional<User> userOpt = usersRepos.findById(userId);

        if (eventOpt.isEmpty() || userOpt.isEmpty()) {
            return "redirect:/find";
        }

        Event event = eventOpt.get();
        User user = userOpt.get();

        // join provjera
        if (joiningRepo.existsByEventIdAndUserId(eventId, userId)) {
            return "redirect:/find?error=already_joined";
        }

        // punost eventa
        int currentCount = joiningRepo.countByEventId(eventId);
        if (currentCount >= event.getMaxPlayers()) {
            return "redirect:/find?error=full";
        }

        // join spremanje
        Joining joining = new Joining();
        joining.setEvent(event);
        joining.setUser(user);
        joiningRepo.save(joining);

        return "redirect:/find";
    }

    @PostMapping("/leave")
    public String leave(@RequestParam Integer eventId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        // Onemogućavanje napuštanja eventa koji si organizirao
        Optional<Event> eventOpt = eventRepo.findById(eventId);
        if (eventOpt.isEmpty()) {
            return "redirect:/find";
        }
        if (eventOpt.get().getOrganizator() != null &&
                eventOpt.get().getOrganizator().getId() == userId) {
            return "redirect:/find?error=cant_leave_own_event";
        }

        joiningRepo.deleteByEventIdAndUserId(eventId, userId);

        return "redirect:/find";
    }


    @PostMapping("/delete-event")
    public String deleteEvent(@RequestParam Integer eventId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<Event> eventOpt = eventRepo.findById(eventId);
        if (eventOpt.isEmpty()) {
            return "redirect:/find";
        }

        Event event = eventOpt.get();

        // Samo organizator može obrisati event
        if (event.getOrganizator() == null || event.getOrganizator().getId() != userId) {
            return "redirect:/find?error=not_organizer";
        }

        // Prvo obriši sve joininge, pa onda event
        joiningRepo.deleteByEventId(eventId);
        eventRepo.delete(event);

        return "redirect:/find";
    }
}