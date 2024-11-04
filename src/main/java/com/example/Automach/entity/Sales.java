package com.example.Automach.entity;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    private String customerName;

    private String orderDecision; // For tracking if the order is quoted or confirmed

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // Remove CascadeType.REMOVE
    @JoinTable(
            name = "sales_products",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "prod_id")
    )
    private List<Product> products;

    @ElementCollection
    @CollectionTable(name = "sales_product_quantity", joinColumns = @JoinColumn(name = "sale_id"))
    @Column(name = "quantity")
    private List<Integer> quantities; // Quantity for each product

    private Double totalPrice;

    private Double discountPercent;

    private Double finalPrice;

    private String orderStatus; // manually updated, e.g., pending, shipped, delivered

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp orderCreatedDate;

    @ManyToOne
    @JoinColumn(name = "created_user_id")
    private Users createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp orderDeliveryDate;

    @ManyToOne
    @JoinColumn(name = "updated_user_id")
    private Users updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedDate;

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
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

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public void setOrderDeliveryDate(Timestamp orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public Users getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Users updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Sales(Long saleId, String customerName, String orderDecision, List<Product> products, List<Integer> quantities, Double totalPrice, Double discountPercent, Double finalPrice, String orderStatus, Timestamp orderCreatedDate, Users createdBy, Timestamp orderDeliveryDate, Users updatedBy, Timestamp updatedDate) {
        this.saleId = saleId;
        this.customerName = customerName;
        this.orderDecision = orderDecision;
        this.products = products;
        this.quantities = quantities;
        this.totalPrice = totalPrice;
        this.discountPercent = discountPercent;
        this.finalPrice = finalPrice;
        this.orderStatus = orderStatus;
        this.orderCreatedDate = orderCreatedDate;
        this.createdBy = createdBy;
        this.orderDeliveryDate = orderDeliveryDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    public Sales() {
        super();
    }

    @Override
    public String toString() {
        return "Sales{" +
                "saleId=" + saleId +
                ", customerName='" + customerName + '\'' +
                ", orderDecision='" + orderDecision + '\'' +
                ", products=" + products +
                ", quantities=" + quantities +
                ", totalPrice=" + totalPrice +
                ", discountPercent=" + discountPercent +
                ", finalPrice=" + finalPrice +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderCreatedDate=" + orderCreatedDate +
                ", createdBy=" + createdBy +
                ", orderDeliveryDate=" + orderDeliveryDate +
                ", updatedBy=" + updatedBy +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
