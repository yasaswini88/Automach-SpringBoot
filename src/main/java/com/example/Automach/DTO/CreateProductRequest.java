package com.example.Automach.DTO;

import java.util.Set;

import com.example.Automach.entity.Product;

public class CreateProductRequest {
	private Product product;
    private Set<Long> rawMaterialIds;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Set<Long> getRawMaterialIds() {
		return rawMaterialIds;
	}
	public void setRawMaterialIds(Set<Long> rawMaterialIds) {
		this.rawMaterialIds = rawMaterialIds;
	}

  

}
