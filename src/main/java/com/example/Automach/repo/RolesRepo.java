package com.example.Automach.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Automach.entity.Roles;

public interface RolesRepo extends JpaRepository<Roles, Long> {
    
    Optional<Roles> findByRoleId(Long id);
}
