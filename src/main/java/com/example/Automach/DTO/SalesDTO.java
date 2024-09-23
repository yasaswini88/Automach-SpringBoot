package com.example.Automach.DTO;

import java.sql.Timestamp;
import java.util.List;

public class SalesDTO {

    private String customerName;
    private String orderDecision;
    private List<Long> productIds; // List of product IDs
    private List<Integer> quantities; // List of product quantities
    private Double discountPercent;
    private String orderStatus;
    private Timestamp orderCreatedDate;
    private Long createdUserId;
    private Timestamp orderDeliveryDate;

    // Getters and Setters


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderDecision() {
        return orderDecision;
    }

    public void setOrderDecision(String orderDecision) {
        this.orderDecision = orderDecision;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getOrderCreatedDate() {
        return orderCreatedDate;
    }

    public void setOrderCreatedDate(Timestamp orderCreatedDate) {
        this.orderCreatedDate = orderCreatedDate;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Timestamp getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public void setOrderDeliveryDate(Timestamp orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }
}
