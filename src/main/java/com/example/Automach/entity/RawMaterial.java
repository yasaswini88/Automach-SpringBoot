package com.example.Automach.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String materialName;
    
<<<<<<< HEAD
//    @OneToOne(mappedBy = "rawMaterial", cascade = CascadeType.ALL)
//    @JsonBackReference
//    private ProductRawMaterial productRawMaterial;
=======
    
//    @JsonBackReference
//    @OneToMany(mappedBy = "rawMaterial", cascade = CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
//    private Set<ProductRawMaterial> products = new HashSet<>();
>>>>>>> d4e4a93d12173cff07db2143231dac31cf041e16

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

<<<<<<< HEAD
//    public ProductRawMaterial getProductRawMaterial() {
//        return productRawMaterial;
//    }
//
//    public void setProductRawMaterial(ProductRawMaterial productRawMaterial) {
//        this.productRawMaterial = productRawMaterial;
//    }

//    @Override
//    public String toString() {
//        return "RawMaterial [id=" + id + ", materialName=" + materialName + ", productRawMaterial=" + productRawMaterial + "]";
//    }
=======
//    public Set<ProductRawMaterial> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Set<ProductRawMaterial> products) {
//        this.products = products;
//    }

    @Override
    public String toString() {
        return "RawMaterial [id=" + id + ", materialName=" + materialName + ", products=";
    }
>>>>>>> d4e4a93d12173cff07db2143231dac31cf041e16
}
