package com.boredgame.repos;

import com.boredgame.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepo extends JpaRepository<Event, Integer> {

    // Vraća samo evente sa statusom OPEN
    List<Event> findByStatus(String status);
}