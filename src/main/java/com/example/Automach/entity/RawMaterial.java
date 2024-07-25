package com.example.Automach.entity;

//import java.util.HashSet;
//import java.util.Set;

import jakarta.persistence.*;

@Entity
public class RawMaterial {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long materialId;
	
	private String materialName;

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	@Override
	public String toString() {
		return "RawMaterial [materialId=" + materialId + ", materialName=" + materialName + ", getMaterialId()="
				+ getMaterialId() + ", getMaterialName()=" + getMaterialName() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
//	@ManyToMany(mappedBy = "rawMaterials")
//    private Set<Product> products = new HashSet<>();



	
	

}