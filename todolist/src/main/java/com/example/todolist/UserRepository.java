package com.example.todolist;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Spring automatically writes the query to search for a user by their name!
    Optional<User> findByUsername(String username);
}
