package com.example.Automach.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Automach.DTO.CreateProductRequest;
import com.example.Automach.entity.Product;
import com.example.Automach.entity.RawMaterial;
import com.example.Automach.repo.ProductRepo;
import com.example.Automach.repo.RawMaterialRepo;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin
public class ProductController {
    
    @Autowired
    ProductRepo prodRepo;
    @Autowired
    RawMaterialRepo rawMaterialRepo;

    @PostMapping("/api/products")
    public ResponseEntity<Product> saveProduct(@RequestBody CreateProductRequest product) {
        return new ResponseEntity<>(addProductWithMaterials(product.getProduct(), product.getRawMaterialIds()), HttpStatus.CREATED);
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<Product>> getProduct() {
        return new ResponseEntity<>(prodRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/products/{prodId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long prodId) {
        Optional<Product> product = prodRepo.findById(prodId);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/products/{prodId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long prodId, @RequestBody Product productDetails) {
        Optional<Product> product = prodRepo.findById(prodId);
        if (product.isPresent()) {
            Product existingProduct = product.get();
            existingProduct.setProdName(productDetails.getProdName());
            return new ResponseEntity<>(prodRepo.save(existingProduct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/products/{prodId}/materials")
    public Set<RawMaterial> getRawMaterialsByProductId(@PathVariable Long prodId) {
        return prodRepo.findById(prodId)
                .map(Product::getRawMaterials)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    public Product addProductWithMaterials(Product product, Set<Long> rawMaterialIds) {
        Set<RawMaterial> materials = new HashSet<>();
        for (Long rawMaterialId : rawMaterialIds) {
            RawMaterial rawMaterial = rawMaterialRepo.findById(rawMaterialId)
                    .orElseThrow(() -> new RuntimeException("Material not found with id: " + rawMaterialId));
            materials.add(rawMaterial);
        }
        product.setRawMaterials(materials);
        return prodRepo.save(product);
    }
}
