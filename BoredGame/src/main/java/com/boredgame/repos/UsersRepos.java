package com.boredgame.repos;

import com.boredgame.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepos extends JpaRepository<Users, Long> {

}