package com.example.Automach.repo;

import com.example.Automach.entity.Supplier;
import com.example.Automach.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {

    // Find a supplier by name, ignoring case
    Optional<Supplier> findByNameIgnoreCase(String name);

    // Find suppliers by the raw materials they supply
    List<Supplier> findByRawMaterialsContains(RawMaterial rawMaterial);
}
