package com.boredgame.repos;

import com.boredgame.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepos extends JpaRepository<Quiz, Integer> {
}