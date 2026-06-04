package com.boredgame.repos;

import com.boredgame.entity.Competition;
import com.boredgame.entity.CompetitionJoining;
import com.boredgame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionJoiningRepos extends JpaRepository<CompetitionJoining, Integer> {

    int countByCompetition(Competition competition);

    boolean existsByCompetitionAndUser(Competition competition, User user);

    CompetitionJoining findByCompetitionAndUser(Competition competition, User user);

    void deleteByCompetition(Competition competition);
}