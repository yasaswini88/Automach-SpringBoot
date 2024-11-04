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

    private Double price;  // New field for price

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "product_tag",
//            joinColumns = @JoinColumn(name = "prod_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id")
//    )
//@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})

@JoinTable(
        name = "product_tag",
        joinColumns = @JoinColumn(name = "prod_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
)
private Set<Tag> tags = new HashSet<>();

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Set<ProductRawMaterial> getRawMaterials() {
        return rawMaterials;
    }

    public void setRawMaterials(Set<ProductRawMaterial> rawMaterials) {
        this.rawMaterials = rawMaterials;
    }

//    @Override
//    public String toString() {
//        return "Product [prodId=" + prodId + ", prodName=" + prodName + ", price=" + price + ", category=" + category + ", tags=" + tags + ", rawMaterials=" + rawMaterials + "]";
//    }

    @Override
    public String toString() {
        return "Product [prodId=" + prodId +
                ", prodName=" + prodName +
                ", price=" + price +
                ", categoryId=" + (category != null ? category.getId() : "null") + "]";
    }

}
