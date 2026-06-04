package com.boredgame.controller;

import com.boredgame.entity.*;
import com.boredgame.repos.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EventsPageController {

    private final CompetitionRepos competitionRepository;
    private final QuizRepos quizRepository;
    private final CompetitionJoiningRepos competitionJoiningRepository;
    private final UsersRepos userRepository;

    public EventsPageController(
            CompetitionRepos competitionRepository,
            QuizRepos quizRepository,
            CompetitionJoiningRepos competitionJoiningRepository,
            UsersRepos userRepository
    ) {
        this.competitionRepository = competitionRepository;
        this.quizRepository = quizRepository;
        this.competitionJoiningRepository = competitionJoiningRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/events")
    public String showEventsPage(Model model, HttpSession session) {
        List<Competition> competitions = competitionRepository.findAll();

        Map<Integer, Integer> currentPlayers = new HashMap<>();
        Map<Integer, Boolean> isFull = new HashMap<>();
        Map<Integer, Boolean> userJoined = new HashMap<>();

        Object usernameObj = session.getAttribute("username");
        User user = null;

        if (usernameObj != null) {
            String username = usernameObj.toString();
            user = userRepository.findByUsername(username);
        }

        for (Competition competition : competitions) {
            int count = competitionJoiningRepository.countByCompetition(competition);

            currentPlayers.put(competition.getId(), count);
            isFull.put(competition.getId(), count >= competition.getMaxPlayers());

            if (user != null) {
                userJoined.put(
                        competition.getId(),
                        competitionJoiningRepository.existsByCompetitionAndUser(competition, user)
                );
            } else {
                userJoined.put(competition.getId(), false);
            }
        }

        Object username = session.getAttribute("username");

        model.addAttribute("competitions", competitions);
        model.addAttribute("quizzes", quizRepository.findAll());

        model.addAttribute("currentPlayers", currentPlayers);
        model.addAttribute("isFull", isFull);
        model.addAttribute("userJoined", userJoined);

        model.addAttribute("loggedIn", username != null);
        model.addAttribute("username", username);

        return "events";
    }

    @PostMapping("/joinCompetition")
    public String joinCompetition(@RequestParam Integer competitionId, HttpSession session) {
        Object usernameObj = session.getAttribute("username");

        if (usernameObj == null) {
            return "redirect:/login";
        }

        String username = usernameObj.toString();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return "redirect:/login";
        }

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        int currentPlayers = competitionJoiningRepository.countByCompetition(competition);

        if (currentPlayers >= competition.getMaxPlayers()) {
            return "redirect:/events?error=full";
        }

        if (competitionJoiningRepository.existsByCompetitionAndUser(competition, user)) {
            return "redirect:/events?error=already_joined";
        }

        CompetitionJoining joining = new CompetitionJoining();
        joining.setCompetition(competition);
        joining.setUser(user);

        competitionJoiningRepository.save(joining);

        return "redirect:/events";
    }

    @PostMapping("/leaveCompetition")
    public String leaveCompetition(@RequestParam Integer competitionId, HttpSession session) {
        Object usernameObj = session.getAttribute("username");

        if (usernameObj == null) {
            return "redirect:/login";
        }

        String username = usernameObj.toString();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return "redirect:/login";
        }

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        CompetitionJoining joining =
                competitionJoiningRepository.findByCompetitionAndUser(competition, user);

        if (joining != null) {
            competitionJoiningRepository.delete(joining);
        }

        return "redirect:/events";
    }
}