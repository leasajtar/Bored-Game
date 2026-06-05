package com.boredgame.controller;

import com.boredgame.entity.User;
import com.boredgame.repos.UsersRepos;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ProfileController {

    private final UsersRepos usersRepos;

    // Folder gdje se spremaju slike profila (relativno na working dir)
    private static final String UPLOAD_DIR = "src/main/resources/static/profile-pictures/";

    public ProfileController(UsersRepos usersRepos) {
        this.usersRepos = usersRepos;
    }

    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = usersRepos.findById(userId).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("bio", user.getBio() != null ? user.getBio() : "");
        model.addAttribute("profilePicture", user.getProfilePicture());
        model.addAttribute("loggedIn", true);

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam(required = false) String bio,
                                HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<User> userOpt = usersRepos.findById(userId);
        if (userOpt.isEmpty()) {
            return "redirect:/profile?error";
        }

        User user = userOpt.get();
        if (bio != null && bio.length() > 300) {
            bio = bio.substring(0, 300);
        }
        user.setBio(bio);
        usersRepos.save(user);

        return "redirect:/profile?success";
    }

    @PostMapping("/profile/upload-picture")
    public String uploadPicture(@RequestParam("profilePicture") MultipartFile file,
                                HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        if (file.isEmpty()) {
            return "redirect:/profile?error";
        }

        // Provjeri tip datoteke
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") &&
                !contentType.equals("image/png") &&
                !contentType.equals("image/webp"))) {
            return "redirect:/profile?error";
        }

        Optional<User> userOpt = usersRepos.findById(userId);
        if (userOpt.isEmpty()) {
            return "redirect:/profile?error";
        }

        try {
            // Napravi folder ako ne postoji
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Odredi ekstenziju
            String ext = contentType.equals("image/png") ? ".png" :
                    contentType.equals("image/webp") ? ".webp" : ".jpg";

            // Jedinstveno ime datoteke
            String filename = "user_" + userId + "_" + UUID.randomUUID().toString().substring(0, 8) + ext;

            // Obriši staru sliku ako postoji
            User user = userOpt.get();
            if (user.getProfilePicture() != null) {
                Path oldFile = uploadPath.resolve(user.getProfilePicture());
                try { Files.deleteIfExists(oldFile); } catch (IOException ignored) {}
            }

            // Spremi novu sliku
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Ažuriraj korisnika
            user.setProfilePicture(filename);
            usersRepos.save(user);

        } catch (IOException e) {
            return "redirect:/profile?error";
        }

        return "redirect:/profile?success";
    }
    @GetMapping("/profile/{username}")
    public String publicProfile(@PathVariable String username,
                                HttpSession session,
                                Model model) {

        User user = usersRepos.findByUsername(username);

        if (user == null) {
            return "redirect:/find";
        }

        Integer loggedInId = (Integer) session.getAttribute("userId");

        // If viewing your own profile, redirect to /profile
        if (loggedInId != null && loggedInId.equals(user.getId())) {
            return "redirect:/profile";
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("bio", user.getBio() != null ? user.getBio() : "");
        model.addAttribute("profilePicture", user.getProfilePicture());
        model.addAttribute("loggedIn", loggedInId != null);
        model.addAttribute("navUsername", session.getAttribute("username"));

        return "profile-public";
    }
}