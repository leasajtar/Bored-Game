package com.boredgame.repos;

import com.boredgame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepos extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}