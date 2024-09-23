package com.example.Automach.repo;

import com.example.Automach.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    // Custom method to find by productId and sort by SKU counter for SKU generation
    Inventory findTopByProductProdIdOrderBySkuCounterDesc(Long productId);
    // Find by product ID
    List<Inventory> findByProductProdId(Long productId);

    // Find by SKU
    Optional<Inventory> findBySku(String sku);
}
