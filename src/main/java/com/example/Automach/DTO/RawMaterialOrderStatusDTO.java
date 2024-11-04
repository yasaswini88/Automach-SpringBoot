package com.example.Automach.DTO;

import java.sql.Timestamp;

public class RawMaterialOrderStatusDTO {
    private Long orderId;
    private Long rawMaterialId;
    private String rawMaterialName;
    private int rawMaterialQuantity;
    private Long supplierId;
    private String supplierName;
    private String status;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

//    private String trackingInfo;
    private String notes;
    private Long createdBy;
    private Long updatedBy;
    private Timestamp createdDate;
    private Timestamp updatedDate;

    // Getters and setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(Long rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public String getRawMaterialName() {
        return rawMaterialName;
    }

    public void setRawMaterialName(String rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
    }

    public int getRawMaterialQuantity() {
        return rawMaterialQuantity;
    }

    public void setRawMaterialQuantity(int rawMaterialQuantity) {
        this.rawMaterialQuantity = rawMaterialQuantity;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public String getTrackingInfo() {
//        return trackingInfo;
//    }
//
//    public void setTrackingInfo(String trackingInfo) {
//        this.trackingInfo = trackingInfo;
//    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }
}
