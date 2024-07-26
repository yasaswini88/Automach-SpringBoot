package com.example.Automach.entity;

import jakarta.persistence.*;
import java.util.Date;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class RawMaterialStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long raw_material_stock_id;
    

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private RawMaterial rawMaterial;

    private int quantity;

    @Temporal(TemporalType.DATE)
    private Date dateModified;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users modifiedBy;

	public Long getRaw_material_stock_id() {
		return raw_material_stock_id;
	}

	public void setRaw_material_stock_id(Long raw_material_stock_id) {
		this.raw_material_stock_id = raw_material_stock_id;
	}

	public RawMaterial getRawMaterial() {
		return rawMaterial;
	}

	public RawMaterialStock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setRawMaterial(RawMaterial rawMaterial) {
		this.rawMaterial = rawMaterial;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Users getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Users modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return "RawMaterialStock [raw_material_stock_id=" + raw_material_stock_id + ", rawMaterial=" + rawMaterial
				+ ", quantity=" + quantity + ", dateModified=" + dateModified + ", modifiedBy=" + modifiedBy
				+ ", getRaw_material_stock_id()=" + getRaw_material_stock_id() + ", getRawMaterial()="
				+ getRawMaterial() + ", getQuantity()=" + getQuantity() + ", getDateModified()=" + getDateModified()
				+ ", getModifiedBy()=" + getModifiedBy() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public RawMaterialStock(Long raw_material_stock_id, RawMaterial rawMaterial, int quantity, Date dateModified,
			Users modifiedBy) {
		super();
		this.raw_material_stock_id = raw_material_stock_id;
		this.rawMaterial = rawMaterial;
		this.quantity = quantity;
		this.dateModified = dateModified;
		this.modifiedBy = modifiedBy;
	}
}