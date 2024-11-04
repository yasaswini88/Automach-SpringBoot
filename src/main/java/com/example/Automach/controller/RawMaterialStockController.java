package com.example.Automach.controller;

import com.example.Automach.DTO.CreateStockRequest;
import com.example.Automach.entity.RawMaterial;
import com.example.Automach.entity.RawMaterialStock;
import com.example.Automach.entity.Users;
import com.example.Automach.Service.RawMaterialStockService;
import com.example.Automach.repo.RawMaterialRepo;
import com.example.Automach.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rawMaterialStock")
public class RawMaterialStockController {

    @Autowired
    private RawMaterialStockService rawMaterialStockService;

    @Autowired
    private RawMaterialRepo rawMaterialRepo;

    @Autowired
    private UserRepo usersRepo;

    // Get all RawMaterialStocks
    @GetMapping
    public List<RawMaterialStock> getAllRawMaterialStocks() {
        return rawMaterialStockService.getAllRawMaterialStocks();
    }

    // Get a RawMaterialStock by ID
    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialStock> getRawMaterialStockById(@PathVariable Long id) {
        Optional<RawMaterialStock> rawMaterialStock = rawMaterialStockService.getRawMaterialStockById(id);
        return rawMaterialStock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/material/{materialName}")
    public ResponseEntity<RawMaterialStock> getRawMaterialStockByMaterialName(@PathVariable String materialName) {
        Optional<RawMaterialStock> rawMaterialStock = rawMaterialStockService.getRawMaterialStockByMaterialName(materialName);
        return rawMaterialStock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new RawMaterialStock
    @PostMapping
    public ResponseEntity<String> createRawMaterialStock(@RequestBody CreateStockRequest createStockRequest) {
        // Validate user ID
        if (createStockRequest.getModifiedByUserId() == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }

        // Find the User by ID
        Users modifiedBy = usersRepo.findById(createStockRequest.getModifiedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        StringBuilder alertMessages = new StringBuilder();

        // Loop through each raw material in the request
        for (Map.Entry<Long, Integer> entry : createStockRequest.getRawMaterialQuantities().entrySet()) {
            // Validate raw material ID
            if (entry.getKey() == null) {
                throw new IllegalArgumentException("Raw material ID must not be null");
            }

            // Find the RawMaterial by ID
            RawMaterial rawMaterial = rawMaterialRepo.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("RawMaterial not found"));

            // Create a new RawMaterialStock instance
            RawMaterialStock rawMaterialStock = new RawMaterialStock();
            rawMaterialStock.setRawMaterial(rawMaterial);
            rawMaterialStock.setQuantity(entry.getValue());
            rawMaterialStock.setDateModified(createStockRequest.getDateModified());
            rawMaterialStock.setModifiedBy(modifiedBy);
            rawMaterialStock.setMinQuantity(createStockRequest.getMinQuantity());

            // Save the RawMaterialStock instance
            RawMaterialStock savedStock = rawMaterialStockService.saveRawMaterialStock(rawMaterialStock);

            // Check for low stock and add to alert messages
            if (savedStock.getQuantity() < savedStock.getMinQuantity()) {
                alertMessages.append("Low stock alert for ")
                        .append(savedStock.getRawMaterial().getMaterialName())
                        .append(": Current quantity is ")
                        .append(savedStock.getQuantity())
                        .append(", below minimum of ")
                        .append(savedStock.getMinQuantity())
                        .append(".\n");
            }
        }

        return ResponseEntity.ok(alertMessages.toString().isEmpty() ? "Stock created successfully" : alertMessages.toString());
    }

    // Update an existing RawMaterialStock
    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialStock> updateRawMaterialStock(@PathVariable Long id, @RequestBody RawMaterialStock rawMaterialStockDetails) {
        RawMaterialStock existingRawMaterialStock = rawMaterialStockService.getRawMaterialStockById(id)
                .orElseThrow(() -> new RuntimeException("RawMaterialStock not found"));

        existingRawMaterialStock.setQuantity(rawMaterialStockDetails.getQuantity());
        existingRawMaterialStock.setMinQuantity(rawMaterialStockDetails.getMinQuantity());
        existingRawMaterialStock.setDateModified(Timestamp.from(Instant.now()));
        existingRawMaterialStock.setModifiedBy(rawMaterialStockDetails.getModifiedBy());

        RawMaterialStock updatedRawMaterialStock = rawMaterialStockService.saveRawMaterialStock(existingRawMaterialStock);


        return ResponseEntity.ok(updatedRawMaterialStock);
    }
}
