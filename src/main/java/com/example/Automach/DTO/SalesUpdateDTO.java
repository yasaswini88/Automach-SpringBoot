package com.example.Automach.DTO;

import java.sql.Timestamp;
import java.util.List;

public class SalesUpdateDTO {

    private String customerName;
    private String orderDecision;
    private List<Long> productIds; // List of product IDs
    private List<Integer> quantities; // List of product quantities
    private Double discountPercent;
    private String orderStatus;
    private Timestamp orderDeliveryDate;
    private Long updatedUserId;
    private Timestamp updatedDate;
    private List<Integer> oldQuantities; // Add this field
    private boolean orderDecisionChangedToConfirmed;

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

    public Timestamp getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public void setOrderDeliveryDate(Timestamp orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public Long getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Long updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<Integer> getOldQuantities() {
        return oldQuantities;
    }

    public void setOldQuantities(List<Integer> oldQuantities) {
        this.oldQuantities = oldQuantities;
    }

    public boolean isOrderDecisionChangedToConfirmed() {
        return orderDecisionChangedToConfirmed;
    }

    public void setOrderDecisionChangedToConfirmed(boolean orderDecisionChangedToConfirmed) {
        this.orderDecisionChangedToConfirmed = orderDecisionChangedToConfirmed;
    }

    @Override
    public String toString() {
        return "SalesUpdateDTO{" +
                "customerName='" + customerName + '\'' +
                ", orderDecision='" + orderDecision + '\'' +
                ", productIds=" + productIds +
                ", quantities=" + quantities +
                ", discountPercent=" + discountPercent +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDeliveryDate=" + orderDeliveryDate +
                ", updatedUserId=" + updatedUserId +
                ", updatedDate=" + updatedDate +
                ", oldQuantities=" + oldQuantities +
                ", orderDecisionChangedToConfirmed=" + orderDecisionChangedToConfirmed +
                '}';
    }
}
