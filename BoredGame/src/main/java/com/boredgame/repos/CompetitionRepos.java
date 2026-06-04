package com.boredgame.repos;

import com.boredgame.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepos extends JpaRepository<Competition, Integer> {
}