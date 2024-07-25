package com.example.Automach.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Automach.DTO.CreateProductRequest;
import com.example.Automach.entity.Product;
import com.example.Automach.entity.ProductRawMaterial;
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
    public ResponseEntity<Product> saveProduct(@RequestBody CreateProductRequest productRequest) {
        return new ResponseEntity<>(addProductWithMaterials(productRequest.getProduct(), productRequest.getRawMaterialQuantities()), HttpStatus.CREATED);
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
    public ResponseEntity<Set<ProductRawMaterial>> getRawMaterialsByProductId(@PathVariable Long prodId) {
        Optional<Product> product = prodRepo.findById(prodId);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get().getRawMaterials(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public Product addProductWithMaterials(Product product, Map<Long, Integer> rawMaterialQuantities) {
        Set<ProductRawMaterial> materials = new HashSet<>();
        for (Map.Entry<Long, Integer> entry : rawMaterialQuantities.entrySet()) {
            Long rawMaterialId = entry.getKey();
            Integer quantity = entry.getValue();
            RawMaterial rawMaterial = rawMaterialRepo.findById(rawMaterialId)
                    .orElseThrow(() -> new RuntimeException("Material not found with id: " + rawMaterialId));
            ProductRawMaterial productRawMaterial = new ProductRawMaterial();
            productRawMaterial.setProduct(product);
            productRawMaterial.setRawMaterial(rawMaterial);
            productRawMaterial.setRawMaterialQuantity(quantity);
            materials.add(productRawMaterial);
        }
        product.setRawMaterials(materials);
        return prodRepo.save(product);
    }
}
