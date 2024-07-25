package com.example.Automach.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prodId;
	private String prodName;
	
    @OneToMany
    @JoinTable(
        name = "product_raw_material",
        joinColumns = @JoinColumn(name = "prod_id"),
        inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private Set<RawMaterial> rawMaterials = new HashSet<>();

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Set<RawMaterial> getRawMaterials() {
		return rawMaterials;
	}

	public void setRawMaterials(Set<RawMaterial> rawMaterials) {
		this.rawMaterials = rawMaterials;
	}

	@Override
	public String toString() {
		return "Product [prodId=" + prodId + ", prodName=" + prodName + ", rawMaterials=" + rawMaterials + "]";
	}
	
	
	
	
}
