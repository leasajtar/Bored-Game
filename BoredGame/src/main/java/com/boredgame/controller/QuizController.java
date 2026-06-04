package com.boredgame.controller;

import com.boredgame.entity.*;
import com.boredgame.repos.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizRepos quizRepository;
    private final CafeRepos cafeRepository;

    public QuizController(QuizRepos quizRepository, CafeRepos cafeRepository) {
        this.quizRepository = quizRepository;
        this.cafeRepository = cafeRepository;
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody QuizRequest request) {
        Cafe cafe = cafeRepository.findById(request.getCafe_id())
                .orElseThrow(() -> new RuntimeException("Cafe not found"));

        Quiz quiz = new Quiz();
        quiz.setDescription(request.getDescription());
        quiz.setDate(LocalDate.parse(request.getDate()));
        quiz.setTime(LocalTime.parse(request.getTime()));
        quiz.setEntryFee(request.getEntry_fee());
        quiz.setCafe(cafe);

        return quizRepository.save(quiz);
    }
}