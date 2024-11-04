package com.example.Automach.controller;

import java.util.*;

import com.example.Automach.entity.*;
import com.example.Automach.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Automach.DTO.CreateProductRequest;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin
public class ProductController {
    @Autowired
    ProductRepo prodRepo;
    @Autowired
    RawMaterialRepo rawMaterialRepo;
    @Autowired
    TagRepo tagRepo;
    @Autowired
    SalesRepo salesRepo;

    @Autowired
    ProductRawMaterialRepo productRawMaterialRepo;

    @Autowired
    InventoryRepo inventoryRepo;

    @PostMapping("/api/products")
    public ResponseEntity<Product> saveProduct(@RequestBody CreateProductRequest productRequest) {
        System.out.println(productRequest.toString());
        Product savedProduct = addProductWithMaterialsAndDetails(
                productRequest.getProduct(),
                productRequest.getRawMaterialQuantities(),
                productRequest.getProduct().getCategory(),
                productRequest.getProduct().getTags()
        );
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
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

    @GetMapping("/api/products/name/{prodName}")
    public ResponseEntity<Product> getProductByName(@PathVariable String prodName) {
        Optional<Product> product = prodRepo.findByProdNameIgnoreCase(prodName.toLowerCase());
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
            existingProduct.setPrice(productDetails.getPrice()); // Added the line to update price
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


    @DeleteMapping("/api/products/{prodId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long prodId) {
        Optional<Product> productOptional = prodRepo.findById(prodId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Remove product from all sales entities
            List<Sales> salesList = salesRepo.findAllByProductsContaining(product);
            for (Sales sale : salesList) {
                sale.getProducts().remove(product);
                salesRepo.save(sale);  // Update the sales entity to remove the product reference
            }

            // Clear associations with tags
            product.getTags().clear();
            prodRepo.save(product);  // Save to update the join table

            // Now delete the product itself
            prodRepo.delete(product);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Transactional
    public Product addProductWithMaterialsAndDetails(Product product, Map<Long, Integer> rawMaterialQuantities, Category category, Set<Tag> tags) {
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

        // Set category and tags
        product.setCategory(category);
        Set<Tag> managedTags = new HashSet<>();
        for (Tag tag : tags) {
            Tag managedTag = tagRepo.findById(tag.getId())
                    .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tag.getId()));
            managedTags.add(managedTag);
        }
        product.setTags(managedTags);

        return prodRepo.save(product);
    }

}
