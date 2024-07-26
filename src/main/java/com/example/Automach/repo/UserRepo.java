package com.example.Automach.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Automach.entity.Users;

public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByUserId(Long id);
    Optional<Users> findByEmail(String email);
}



