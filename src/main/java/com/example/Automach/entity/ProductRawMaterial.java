package com.example.Automach.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_raw_material")
public class ProductRawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prod_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private RawMaterial rawMaterial;

    private int rawMaterialQuantity;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public int getRawMaterialQuantity() {
        return rawMaterialQuantity;
    }

    public void setRawMaterialQuantity(int rawMaterialQuantity) {
        this.rawMaterialQuantity = rawMaterialQuantity;
    }

    @Override
    public String toString() {
        return "ProductRawMaterial [id=" + id + ", product=" + product + ", rawMaterial=" + rawMaterial + ", rawMaterialQuantity=" + rawMaterialQuantity + "]";
    }
}
