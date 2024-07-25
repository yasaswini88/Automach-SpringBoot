package com.example.Automach.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Automach.entity.RawMaterial;

public interface RawMaterialRepo extends JpaRepository<RawMaterial, Long> {
}