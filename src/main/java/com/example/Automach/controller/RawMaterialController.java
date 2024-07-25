package com.example.Automach.controller;

import com.example.Automach.entity.RawMaterial;
import com.example.Automach.repo.RawMaterialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RawMaterialController {

    @Autowired
    RawMaterialRepo rawMaterialRepo;

    @PostMapping("/rawmaterials")
    public ResponseEntity<RawMaterial> saveRawMaterial(@RequestBody RawMaterial rawMaterial) {
        return new ResponseEntity<>(rawMaterialRepo.save(rawMaterial), HttpStatus.CREATED);
    }

    @GetMapping("/rawmaterials")
    public ResponseEntity<List<RawMaterial>> getRawMaterials() {
        return new ResponseEntity<>(rawMaterialRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/rawmaterials/{materialId}")
    public ResponseEntity<RawMaterial> getRawMaterial(@PathVariable Long materialId) {
        Optional<RawMaterial> rawMaterial = rawMaterialRepo.findById(materialId);
        return rawMaterial.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/rawmaterials/{materialId}")
    public ResponseEntity<RawMaterial> updateRawMaterial(@PathVariable Long materialId, @RequestBody RawMaterial rawMaterialDetails) {
        Optional<RawMaterial> rawMaterial = rawMaterialRepo.findById(materialId);
        if (rawMaterial.isPresent()) {
            RawMaterial existingRawMaterial = rawMaterial.get();
            existingRawMaterial.setMaterialName(rawMaterialDetails.getMaterialName());
            return new ResponseEntity<>(rawMaterialRepo.save(existingRawMaterial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/rawmaterials/{materialId}")
    public ResponseEntity<Void> deleteRawMaterial(@PathVariable Long materialId) {
        if (rawMaterialRepo.existsById(materialId)) {
            rawMaterialRepo.deleteById(materialId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
