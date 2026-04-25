package com.boredgame.repos;

import com.boredgame.entity.Joining;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoiningRepo extends JpaRepository<Joining, Integer> {
    // Postoji li već pridruzivanje za ovog korisnika na ovom dogadaju?
    boolean existsByEventIdAndUserId(Integer eventId, Integer userId);
}
