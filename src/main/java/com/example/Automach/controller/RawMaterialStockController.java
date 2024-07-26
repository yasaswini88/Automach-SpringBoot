package com.example.Automach.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Automach.Service.RawMaterialStockService;
import com.example.Automach.entity.RawMaterialStock;

@RestController
@RequestMapping("/api/rawMaterialStock")
public class RawMaterialStockController {

    @Autowired
    private RawMaterialStockService rawMaterialStockService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public RawMaterialStock createRawMaterialStock(@RequestBody RawMaterialStock rawMaterialStock) {
        return rawMaterialStockService.saveRawMaterialStock(rawMaterialStock);
    }

    @GetMapping
    public List<RawMaterialStock> getAllRawMaterialStocks() {
        return rawMaterialStockService.getAllRawMaterialStocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialStock> getRawMaterialStockById(@PathVariable Long id) {
        Optional<RawMaterialStock> rawMaterialStock = rawMaterialStockService.getRawMaterialStockById(id);
        return rawMaterialStock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialStock> updateRawMaterialStock(@PathVariable Long id, @RequestBody RawMaterialStock rawMaterialStockDetails) {
        try {
            RawMaterialStock updatedRawMaterialStock = rawMaterialStockService.updateRawMaterialStock(id, rawMaterialStockDetails);
            return ResponseEntity.ok(updatedRawMaterialStock);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRawMaterialStock(@PathVariable Long id) {
        rawMaterialStockService.deleteRawMaterialStock(id);
        return ResponseEntity.noContent().build();
    }
}
