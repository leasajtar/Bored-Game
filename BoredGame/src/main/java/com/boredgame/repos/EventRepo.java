package com.boredgame.repos;

import com.boredgame.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepo extends JpaRepository<Event, Integer> {

    List<Event> findByStatus(String status);
}