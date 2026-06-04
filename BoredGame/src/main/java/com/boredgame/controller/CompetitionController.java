// CompetitionController.java
package com.boredgame.controller;

import com.boredgame.entity.*;
import com.boredgame.repos.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/competitions")
public class CompetitionController {

    private final CompetitionRepos competitionRepository;
    private final CafeRepos cafeRepository;

    public CompetitionController(CompetitionRepos competitionRepository, CafeRepos cafeRepository) {
        this.competitionRepository = competitionRepository;
        this.cafeRepository = cafeRepository;
    }

    @GetMapping
    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    @PostMapping
    public Competition createCompetition(@RequestBody CompetitionRequest request) {
        Cafe cafe = cafeRepository.findById(request.getCafe_id())
                .orElseThrow(() -> new RuntimeException("Cafe not found"));

        Competition competition = new Competition();
        competition.setGameType(request.getGame_type());
        competition.setDate(LocalDate.parse(request.getDate()));
        competition.setTime(LocalTime.parse(request.getTime()));
        competition.setEntryFee(request.getEntry_fee());
        competition.setMaxPlayers(request.getMax_players());
        competition.setCafe(cafe);

        return competitionRepository.save(competition);
    }
}