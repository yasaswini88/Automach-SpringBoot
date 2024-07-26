package com.example.Automach.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Automach.entity.RawMaterial;

public interface RawMaterialRepo extends JpaRepository<RawMaterial, Long> {
	
	@Query(value = "SELECT * FROM raw_material", nativeQuery = true)
    List<RawMaterial> findAllRawMaterials();
	

	Optional<RawMaterial> findByMaterialName(String materialName);
	
}



