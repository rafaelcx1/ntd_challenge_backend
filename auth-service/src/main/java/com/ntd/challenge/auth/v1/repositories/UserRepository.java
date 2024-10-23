package com.ntd.challenge.auth.v1.repositories;

import com.ntd.challenge.auth.v1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);
}
