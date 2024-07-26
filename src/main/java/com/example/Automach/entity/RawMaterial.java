package com.example.Automach.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String materialName;
    
    
//    @JsonBackReference
//    @OneToMany(mappedBy = "rawMaterial", cascade = CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
//    private Set<ProductRawMaterial> products = new HashSet<>();

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
}
