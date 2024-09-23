package  com.example.Automach.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    private String sku;  // SKU, generated as "prodId + 5-digit counter"

    private Integer quantity; // Total quantity of this product

    private String status; // Product status like "Available", "Damaged", etc.

    @ManyToOne
    @JoinColumn(name = "prod_id", nullable = false)
    private Product product;  // Relationship with Product entity

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userModified;  // User who last modified this inventory

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;  // Date when the inventory was last modified

    // SKU counter stored in DB to avoid duplication after restart
    private Integer skuCounter;

    // Getters and Setters
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Users getUserModified() {
        return userModified;
    }

    public void setUserModified(Users userModified) {
        this.userModified = userModified;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getSkuCounter() {
        return skuCounter;
    }

    public void setSkuCounter(Integer skuCounter) {
        this.skuCounter = skuCounter;
    }

    public Inventory(Long inventoryId, String sku, Integer quantity, String status, Product product, Users userModified, Date modifiedDate, Integer skuCounter) {
        this.inventoryId = inventoryId;
        this.sku = sku;
        this.quantity = quantity;
        this.status = status;
        this.product = product;
        this.userModified = userModified;
        this.modifiedDate = modifiedDate;
        this.skuCounter = skuCounter;
    }

    public Inventory() {
        super();
    }
}
