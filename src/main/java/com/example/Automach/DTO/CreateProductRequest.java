package com.example.Automach.DTO;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.example.Automach.entity.Product;
import com.example.Automach.entity.Category;
import com.example.Automach.entity.Tag;

public class CreateProductRequest {
    private Product product;
    private Map<Long, Integer> rawMaterialQuantities;
    private Category category;
    private Set<Tag> tags;
    private Double price;  // New field for price

    // Default constructor
    public CreateProductRequest() {
    }

    // Parameterized constructor
    public CreateProductRequest(Product product, Map<Long, Integer> rawMaterialQuantities, Category category, Set<Tag> tags, Double price) {
        this.product = product;
        this.rawMaterialQuantities = rawMaterialQuantities;
        this.category = category;
        this.tags = tags;
        this.price = price;  // Set price
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CreateProductRequest{" +
                "product=" + product +
                ", rawMaterialQuantities=" + rawMaterialQuantities +
                ", category=" + category +
                ", tags=" + tags +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateProductRequest that = (CreateProductRequest) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(rawMaterialQuantities, that.rawMaterialQuantities) &&
                Objects.equals(category, that.category) &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, rawMaterialQuantities, category, tags, price);
    }
}
