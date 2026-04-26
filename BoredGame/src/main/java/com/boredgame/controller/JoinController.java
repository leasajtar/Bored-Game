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

        // 1. Provjeri je li korisnik ulogiran
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login"; // nije ulogiran, šalji na login
        }

        Optional<Event> eventOpt = eventRepo.findById(eventId);
        Optional<User> userOpt = usersRepos.findById(userId);

        if (eventOpt.isEmpty() || userOpt.isEmpty()) {
            return "redirect:/find";
        }

        Event event = eventOpt.get();
        User user = userOpt.get();

        // 2. Provjeri je li već joinao
        if (joiningRepo.existsByEventIdAndUserId(eventId, userId)) {
            return "redirect:/find?error=already_joined";
        }

        // 3. Provjeri je li event pun (maxPlayers uključuje organizatora)
        int currentCount = joiningRepo.countByEventId(eventId) + 1; // +1 za organizatora
        if (currentCount >= event.getMaxPlayers()) {
            return "redirect:/find?error=full";
        }

        // 4. Sve ok - spremi joining
        Joining joining = new Joining();
        joining.setEvent(event);
        joining.setUser(user);
        joiningRepo.save(joining);

        return "redirect:/find";
    }
}