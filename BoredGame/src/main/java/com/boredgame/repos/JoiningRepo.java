package com.boredgame.repos;

import com.boredgame.entity.Joining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface JoiningRepo extends JpaRepository<Joining, Integer> {

    boolean existsByEventIdAndUserId(Integer eventId, Integer userId);

    int countByEventId(Integer eventId);

    @Transactional
    void deleteByEventIdAndUserId(Integer eventId, Integer userId);
}
