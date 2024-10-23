package com.example.Automach.Service;

import com.example.Automach.entity.RawMaterialStock;
import com.example.Automach.repo.RawMaterialStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RawMaterialStockService {

    @Autowired
    private RawMaterialStockRepo rawMaterialStockRepository;

    public List<RawMaterialStock> getAllRawMaterialStocks() {
        return rawMaterialStockRepository.findAll();
    }

    public Optional<RawMaterialStock> getRawMaterialStockById(Long id) {
        return rawMaterialStockRepository.findById(id);
    }

    public Optional<RawMaterialStock> getRawMaterialStockByMaterialName(String materialName) {
        return rawMaterialStockRepository.findByRawMaterialMaterialName(materialName);
    }

    public RawMaterialStock saveRawMaterialStock(RawMaterialStock rawMaterialStock) {
        return rawMaterialStockRepository.save(rawMaterialStock);
    }

}
