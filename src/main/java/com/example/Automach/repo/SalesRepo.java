package com.example.Automach.repo;

import com.example.Automach.entity.Product;
import com.example.Automach.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepo extends JpaRepository<Sales, Long> {
    List<Sales> findAllByProductsContaining(Product product);
}
