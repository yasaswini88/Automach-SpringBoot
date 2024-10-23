package com.example.Automach.DTO;

import java.util.Date;

public class InventoryDTO {

    private Long inventoryId;
    private String sku;
    private Integer quantity;
    private Integer blockedQuantity;
    private Integer requiredQuantity;
//    private String status;
    private Long productId;
    private Long userId;
    private Date modifiedDate;
    private Integer skuCounter;

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

    public Integer getBlockedQuantity() {
        return blockedQuantity;
    }

    public void setBlockedQuantity(Integer blockedQuantity) {
        this.blockedQuantity = blockedQuantity;
    }

    public Integer getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Integer requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
