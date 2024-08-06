package com.example.Automach.Service;

import com.example.Automach.entity.RawMaterialStock;
import com.example.Automach.repo.RawMaterialStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class RawMaterialStockService {

    @Autowired
    private RawMaterialStockRepo rawMaterialStockRepo;

    public List<RawMaterialStock> getAllRawMaterialStocks() {
        return rawMaterialStockRepo.findAll();
    }

    public Optional<RawMaterialStock> getRawMaterialStockById(Long id) {
        return rawMaterialStockRepo.findById(id);
    }

    public RawMaterialStock saveRawMaterialStock(RawMaterialStock rawMaterialStock) {
        return rawMaterialStockRepo.save(rawMaterialStock);
    }

    public RawMaterialStock updateRawMaterialStock(Long id, RawMaterialStock rawMaterialStockDetails) {
        RawMaterialStock rawMaterialStock = rawMaterialStockRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("RawMaterialStock not found"));
        rawMaterialStock.setRawMaterial(rawMaterialStockDetails.getRawMaterial());
        rawMaterialStock.setQuantity(rawMaterialStockDetails.getQuantity());
        rawMaterialStock.setDateModified(Timestamp.from(Instant.now()));
        rawMaterialStock.setModifiedBy(rawMaterialStockDetails.getModifiedBy());
        return rawMaterialStockRepo.save(rawMaterialStock);
    }

    public void deleteRawMaterialStock(Long id) {
        rawMaterialStockRepo.deleteById(id);
    }
}
