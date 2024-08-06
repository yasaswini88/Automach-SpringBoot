package com.example.Automach.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class RawMaterialStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long raw_material_stock_id;

//	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "id", nullable = false)
	private RawMaterial rawMaterial;

	private int quantity;

	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp dateModified;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Users modifiedBy;

	// Getters and setters

	public Long getRaw_material_stock_id() {
		return raw_material_stock_id;
	}

	public void setRaw_material_stock_id(Long raw_material_stock_id) {
		this.raw_material_stock_id = raw_material_stock_id;
	}

	public RawMaterial getRawMaterial() {
		return rawMaterial;
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

	public Timestamp getDateModified() {
		return dateModified;
	}

	public void setDateModified(Timestamp dateModified) {
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
		return "RawMaterialStock{" +
				"raw_material_stock_id=" + raw_material_stock_id +
				", rawMaterial=" + rawMaterial +
				", quantity=" + quantity +
				", dateModified=" + dateModified +
				", modifiedBy=" + modifiedBy +
				'}';
	}

	public RawMaterialStock() {
		super();
	}
}
