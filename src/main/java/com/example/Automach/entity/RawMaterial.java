package com.example.Automach.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String materialName;
    
//    @OneToOne(mappedBy = "rawMaterial", cascade = CascadeType.ALL)
//    @JsonBackReference
//    private ProductRawMaterial productRawMaterial;

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
}
