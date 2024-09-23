package com.example.Automach.controller;

import com.example.Automach.DTO.InventoryDTO;
import com.example.Automach.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Add or update inventory
    @PostMapping("/add")
    public ResponseEntity<InventoryDTO> addOrUpdateInventory(@RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO result = inventoryService.addOrUpdateInventory(inventoryDTO);
        return ResponseEntity.ok(result);
    }

//    // Fetch inventory by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) {
//        InventoryDTO result = inventoryService.getInventoryById(id);
//        return ResponseEntity.ok(result);
//    }
//
//    // Fetch inventory by product ID
//    @GetMapping("/product/{productId}")
//    public ResponseEntity<List<InventoryDTO>> getInventoryByProductId(@PathVariable Long productId) {
//        List<InventoryDTO> result = inventoryService.getInventoryByProductId(productId);
//        return ResponseEntity.ok(result);
//    }
//
//    // Fetch inventory by SKU
//    @GetMapping("/sku/{sku}")
//    public ResponseEntity<InventoryDTO> getInventoryBySku(@PathVariable String sku) {
//        InventoryDTO result = inventoryService.getInventoryBySku(sku);
//        return ResponseEntity.ok(result);
//    }
//
//    // Update status of inventory item
//    @PutMapping("/{id}/status")
//    public ResponseEntity<InventoryDTO> updateInventoryStatus(@PathVariable Long id, @RequestParam String status) {
//        InventoryDTO result = inventoryService.updateInventoryStatus(id, status);
//        return ResponseEntity.ok(result);
//    }
//
//    // Delete inventory by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteInventoryById(@PathVariable Long id) {
//        inventoryService.deleteInventoryById(id);
//        return ResponseEntity.noContent().build();
//    }
}

