package com.boredgame.repos;

import com.boredgame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepos extends JpaRepository<User, Long> {

    User findByUsername(String username);
}