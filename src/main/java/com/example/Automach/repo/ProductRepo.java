package com.example.Automach.repo;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Automach.entity.Product;
//import com.example.Automach.entity.Roles;


public interface ProductRepo extends JpaRepository<Product, Long>{
	

}


