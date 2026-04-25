package com.boredgame.repos;

import com.boredgame.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {
    // Spring čita naziv metode i sam napiše:
    // SELECT * FROM dogadaji WHERE status = ? ORDER BY datum_vrijeme ASC
    List<Event> findByStatusOrderByDatumVrijemeAsc(String status);

}
