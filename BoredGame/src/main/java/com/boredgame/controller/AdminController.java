package com.boredgame.controller;

import com.boredgame.entity.Admin;
import com.boredgame.entity.Cafe;
import com.boredgame.entity.Competition;
import com.boredgame.entity.Quiz;
import com.boredgame.repos.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminRepos adminRepository;

    private final CompetitionRepos competitionRepository;
    private final CafeRepos cafeRepository;

    private final QuizRepos quizRepository;

    private final CompetitionJoiningRepos competitionJoiningRepository;

    public AdminController(
            AdminRepos adminRepository,
            CompetitionRepos competitionRepository,
            CafeRepos cafeRepository,
            QuizRepos quizRepository,
            CompetitionJoiningRepos competitionJoiningRepository
    ) {
        this.adminRepository = adminRepository;
        this.competitionRepository = competitionRepository;
        this.cafeRepository = cafeRepository;
        this.quizRepository = quizRepository;
        this.competitionJoiningRepository = competitionJoiningRepository;
    }

    @GetMapping("/login")
    public String adminLoginPage() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String adminLogin(
            @RequestParam Integer id,
            @RequestParam String password,
            HttpSession session
    ) {
        Admin admin = adminRepository.findById(id).orElse(null);

        if (admin == null || !admin.getPassword().equals(password)) {
            return "redirect:/admin/login?error";
        }

        session.setAttribute("adminLoggedIn", true);
        session.setAttribute("adminID", admin.getId());
        session.setAttribute("adminName", admin.getIme());

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Boolean adminLoggedIn = (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        model.addAttribute("adminName", session.getAttribute("adminName"));

        return "dashboard";
    }

    @GetMapping("/kviz")
    public String kvizPage(HttpSession session, Model model) {
        Boolean adminLoggedIn = (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        model.addAttribute("cafes", cafeRepository.findAll());

        return "kviz";
    }

    @PostMapping("/kviz")
    public String createKviz(
            @RequestParam String description,
            @RequestParam String date,
            @RequestParam String time,
            @RequestParam Double entry_fee,
            @RequestParam Integer cafe_id,
            HttpSession session
    ) {
        Boolean adminLoggedIn = (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        Cafe cafe = cafeRepository.findById(cafe_id)
                .orElseThrow(() -> new RuntimeException("Cafe not found"));

        Quiz quiz = new Quiz();
        quiz.setDescription(description);
        quiz.setDate(LocalDate.parse(date));
        quiz.setTime(LocalTime.parse(time));
        quiz.setEntryFee(entry_fee);
        quiz.setCafe(cafe);

        quizRepository.save(quiz);

        return "redirect:/admin/dashboard";
    }
    @PostMapping("/obrisi/kviz")
    public String deleteQuiz(
            @RequestParam Integer quizId,
            @RequestParam(required = false) String confirmDelete,
            HttpSession session
    ) {
        Boolean adminLoggedIn = (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        if (!"YES".equals(confirmDelete)) {
            return "redirect:/admin/obrisi?error=confirm";
        }

        quizRepository.deleteById(quizId);

        return "redirect:/admin/obrisi?success=quiz";
    }
    @GetMapping("/obrisi/kviz/{id}")
    public String confirmQuizDelete(
            @PathVariable Integer id,
            HttpSession session,
            Model model
    ) {
        Boolean adminLoggedIn =
                (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        Quiz quiz =
                quizRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Quiz not found"));

        model.addAttribute("quiz", quiz);

        return "confirm-delete-quiz";
    }

    @PostMapping("/obrisi/kviz/{id}")
    public String deleteQuiz(
            @PathVariable Integer id,
            HttpSession session
    ) {
        Boolean adminLoggedIn =
                (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        quizRepository.deleteById(id);

        return "redirect:/admin/obrisi";
    }

    @GetMapping("/obrisi")
    public String obrisiPage(HttpSession session, Model model) {
        Boolean adminLoggedIn = (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        model.addAttribute("competitions", competitionRepository.findAll());
        model.addAttribute("quizzes", quizRepository.findAll());

        return "obrisi";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @GetMapping("/natjecanje")
    public String natjecanjePage(HttpSession session, Model model) {
        Boolean adminLoggedIn = (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        model.addAttribute("cafes", cafeRepository.findAll());

        return "natjecanje";
    }

    @PostMapping("/natjecanje")
    public String createNatjecanje(
            @RequestParam String game_type,
            @RequestParam String date,
            @RequestParam String time,
            @RequestParam Double entry_fee,
            @RequestParam Integer max_players,
            @RequestParam Integer cafe_id,
            HttpSession session
    ) {
        Boolean adminLoggedIn = (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        if (game_type == null || game_type.isBlank()) {
            return "redirect:/admin/natjecanje?error=game";
        }

        Cafe cafe = cafeRepository.findById(cafe_id)
                .orElseThrow(() -> new RuntimeException("Cafe not found"));

        Competition competition = new Competition();
        competition.setGameType(game_type);
        competition.setDate(LocalDate.parse(date));
        competition.setTime(LocalTime.parse(time));
        competition.setEntryFee(entry_fee);
        competition.setMaxPlayers(max_players);
        competition.setCafe(cafe);

        competitionRepository.save(competition);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/obrisi/natjecanje")
    public String deleteCompetition(
            @RequestParam Integer competitionId,
            @RequestParam(required = false) String confirmDelete,
            HttpSession session
    ) {
        Boolean adminLoggedIn = (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        if (!"YES".equals(confirmDelete)) {
            return "redirect:/admin/obrisi?error=confirm";
        }

        competitionRepository.deleteById(competitionId);

        return "redirect:/admin/obrisi?success=competition";
    }

    @GetMapping("/obrisi/natjecanje/{id}")
    public String confirmCompetitionDelete(
            @PathVariable Integer id,
            HttpSession session,
            Model model
    ) {
        Boolean adminLoggedIn =
                (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        Competition competition =
                competitionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Competition not found"));

        model.addAttribute("competition", competition);

        return "confirm-delete-competition";
    }

    @PostMapping("/obrisi/natjecanje/{id}")
    public String deleteCompetition(
            @PathVariable Integer id,
            HttpSession session
    ) {
        Boolean adminLoggedIn =
                (Boolean) session.getAttribute("adminLoggedIn");

        if (adminLoggedIn == null || !adminLoggedIn) {
            return "redirect:/admin/login";
        }

        Competition competition =
                competitionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Competition not found"));

        competitionJoiningRepository.deleteByCompetition(competition);

        competitionRepository.delete(competition);

        return "redirect:/admin/obrisi";
    }
}