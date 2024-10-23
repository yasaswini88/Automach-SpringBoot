package com.example.Automach.repo;

import com.example.Automach.entity.RawMaterialStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RawMaterialStockRepo extends JpaRepository<RawMaterialStock, Long> {
    Optional<RawMaterialStock> findByRawMaterialMaterialName(String materialName);
}
