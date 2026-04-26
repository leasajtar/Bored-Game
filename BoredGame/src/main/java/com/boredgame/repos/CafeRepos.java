package com.boredgame.repos;

import com.boredgame.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepos extends JpaRepository<Cafe, Integer> {
}