package com.example.Automach.repo;

import com.example.Automach.entity.Inventory;
import com.example.Automach.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    Inventory findTopByProductProdIdOrderBySkuCounterDesc(Long productId);

    Inventory findByProductProdId(Long productId);

    Optional<Inventory> findBySku(String sku);

    List<Inventory> findAllByProduct(Product product);

    //  method to find inventory by product name
//    List<Inventory> findByProductProdName(String prodName);

    List<Inventory> findByProductProdNameIgnoreCase(String prodName);

}
