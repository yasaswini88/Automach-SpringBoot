package com.example.Automach.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodId;
    private String prodName;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductRawMaterial> rawMaterials = new HashSet<>();

    // Getters and Setters
    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Set<ProductRawMaterial> getRawMaterials() {
        return rawMaterials;
    }

    public void setRawMaterials(Set<ProductRawMaterial> rawMaterials) {
        this.rawMaterials = rawMaterials;
    }

    @Override
    public String toString() {
        return "Product [prodId=" + prodId + ", prodName=" + prodName + ", rawMaterials=" + rawMaterials + "]";
    }
}
