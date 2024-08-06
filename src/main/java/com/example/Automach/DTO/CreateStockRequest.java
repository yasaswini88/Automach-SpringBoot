package com.example.Automach.DTO;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

public class CreateStockRequest {

    private Long rawMaterialId;
    private int quantity;
    private Timestamp dateModified;
    private Long modifiedByUserId;
    private Map<Long, Integer> rawMaterialQuantities;

    // Default constructor
    public CreateStockRequest() {
    }

    // Parameterized constructor
    public CreateStockRequest(Long rawMaterialId, int quantity, Timestamp dateModified, Long modifiedByUserId, Map<Long, Integer> rawMaterialQuantities) {
        this.rawMaterialId = rawMaterialId;
        this.quantity = quantity;
        this.dateModified = dateModified;
        this.modifiedByUserId = modifiedByUserId;
        this.rawMaterialQuantities = rawMaterialQuantities;
    }

    // Getters and setters

    public Long getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(Long rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }

    public Long getModifiedByUserId() {
        return modifiedByUserId;
    }

    public void setModifiedByUserId(Long modifiedByUserId) {
        this.modifiedByUserId = modifiedByUserId;
    }

    public Map<Long, Integer> getRawMaterialQuantities() {
        return rawMaterialQuantities;
    }

    public void setRawMaterialQuantities(Map<Long, Integer> rawMaterialQuantities) {
        this.rawMaterialQuantities = rawMaterialQuantities;
    }

    @Override
    public String toString() {
        return "CreateStockRequest{" +
                "rawMaterialId=" + rawMaterialId +
                ", quantity=" + quantity +
                ", dateModified=" + dateModified +
                ", modifiedByUserId=" + modifiedByUserId +
                ", rawMaterialQuantities=" + rawMaterialQuantities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateStockRequest that = (CreateStockRequest) o;
        return quantity == that.quantity &&
                Objects.equals(rawMaterialId, that.rawMaterialId) &&
                Objects.equals(dateModified, that.dateModified) &&
                Objects.equals(modifiedByUserId, that.modifiedByUserId) &&
                Objects.equals(rawMaterialQuantities, that.rawMaterialQuantities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawMaterialId, quantity, dateModified, modifiedByUserId, rawMaterialQuantities);
    }
}
