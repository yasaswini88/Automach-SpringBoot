package com.example.Automach.DTO;

import java.util.Map;
import java.util.Objects;

import com.example.Automach.entity.Product;

public class CreateProductRequest {
    private Product product;
    private Map<Long, Integer> rawMaterialQuantities;

    // Default constructor
    public CreateProductRequest() {
    }

    // Parameterized constructor
    public CreateProductRequest(Product product, Map<Long, Integer> rawMaterialQuantities) {
        this.product = product;
        this.rawMaterialQuantities = rawMaterialQuantities;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Map<Long, Integer> getRawMaterialQuantities() {
        return rawMaterialQuantities;
    }

    public void setRawMaterialQuantities(Map<Long, Integer> rawMaterialQuantities) {
        this.rawMaterialQuantities = rawMaterialQuantities;
    }

    @Override
    public String toString() {
        return "CreateProductRequest{" +
                "product=" + product +
                ", rawMaterialQuantities=" + rawMaterialQuantities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateProductRequest that = (CreateProductRequest) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(rawMaterialQuantities, that.rawMaterialQuantities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, rawMaterialQuantities);
    }
}
