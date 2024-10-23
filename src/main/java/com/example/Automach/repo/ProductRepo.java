package com.example.Automach.repo;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Automach.entity.Product;

import java.util.Optional;
//import com.example.Automach.entity.Roles;


public interface ProductRepo extends JpaRepository<Product, Long>{
    Optional<Product> findByProdNameIgnoreCase(String prodName);
	

}


