package com.example.Automach.repo;

import com.example.Automach.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepo extends JpaRepository<Supplier, Long>{
    Optional<Supplier> findByNameIgnoreCase(String name);
}
