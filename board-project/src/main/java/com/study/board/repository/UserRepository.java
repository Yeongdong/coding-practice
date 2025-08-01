package com.study.board.repository;

import com.study.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByName(String name);

    Optional<User> findByLoginId(String loginId);
}
