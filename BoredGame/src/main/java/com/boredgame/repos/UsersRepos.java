package com.boredgame.repos;

import com.boredgame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepos extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}