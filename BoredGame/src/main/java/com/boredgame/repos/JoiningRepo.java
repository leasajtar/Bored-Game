package com.boredgame.repos;

import com.boredgame.entity.Event;
import com.boredgame.entity.Joining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JoiningRepo extends JpaRepository<Joining, Integer> {

    boolean existsByEventIdAndUserId(Integer eventId, Integer userId);

    int countByEventId(Integer eventId);

    @Transactional
    void deleteByEventIdAndUserId(Integer eventId, Integer userId);

    @Transactional
    void deleteByEventId(Integer eventId);

    List<Joining> findByEventId(Integer eventId);

    void deleteAllByEventIn(List<Event> events);
}
