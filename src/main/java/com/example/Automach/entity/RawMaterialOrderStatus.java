package com.example.Automach.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class RawMaterialOrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "raw_material_id", referencedColumnName = "Id")
    private RawMaterial rawMaterial;

    private int rawMaterialQuantity;

    private String supplierName;
    private String status;
    private String trackingInfo;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "userId")
    private Users createdBy;

    @ManyToOne
    @JoinColumn(name = "updatedBy", referencedColumnName = "userId")
    private Users updatedBy;
    public Long getOrderId() {
        return orderId;
    }

    public RawMaterialOrderStatus() {
        super();
    }

    public RawMaterialOrderStatus(Long orderId, RawMaterial rawMaterial, int rawMaterialQuantity, String supplierName, String status, String trackingInfo, String notes, Users createdBy, Users updatedBy, Timestamp createdDate, Timestamp updatedDate) {
        this.orderId = orderId;
        this.rawMaterial = rawMaterial;
        this.rawMaterialQuantity = rawMaterialQuantity;
        this.supplierName = supplierName;
        this.status = status;
        this.trackingInfo = trackingInfo;
        this.notes = notes;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "RawMaterialOrderStatus{" +
                "orderId=" + orderId +
                ", rawMaterial=" + rawMaterial +
                ", rawMaterialQuantity=" + rawMaterialQuantity +
                ", supplierName='" + supplierName + '\'' +
                ", status='" + status + '\'' +
                ", trackingInfo='" + trackingInfo + '\'' +
                ", notes='" + notes + '\'' +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
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

    public String getTrackingInfo() {
        return trackingInfo;
    }

    public void setTrackingInfo(String trackingInfo) {
        this.trackingInfo = trackingInfo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public Users getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Users updatedBy) {
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

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdDate;



    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedDate;



}


