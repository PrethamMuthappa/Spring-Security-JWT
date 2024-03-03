package org.example.jwt.repository;

import org.example.jwt.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Userrepos extends JpaRepository<User , Integer> {
    Optional<User>findUserByUsername(String username);
}

