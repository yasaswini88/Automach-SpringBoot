package com.example.Automach.DTO;

import java.util.Map;

import com.example.Automach.entity.Product;

public class CreateProductRequest {
    private Product product;
    private Map<Long, Integer> rawMaterialQuantities;

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
}
