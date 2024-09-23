package com.example.Automach.controller;

import com.example.Automach.DTO.SalesDTO;
import com.example.Automach.DTO.SalesUpdateDTO;
import com.example.Automach.Service.SalesService;
import com.example.Automach.entity.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    // POST mapping for creating a new sale
    @PostMapping
    public ResponseEntity<Sales> createSale(@RequestBody SalesDTO salesDTO) {
        Sales createdSale = salesService.createSale(salesDTO);
        return ResponseEntity.ok(createdSale);
    }

    // PUT mapping for updating an existing sale
    @PutMapping("/{id}")
    public ResponseEntity<Sales> updateSale(@PathVariable Long id, @RequestBody SalesUpdateDTO salesUpdateDTO) {
        Sales updatedSale = salesService.updateSale(id, salesUpdateDTO);
        return ResponseEntity.ok(updatedSale);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sales> getSaleById(@PathVariable Long id) {
        Sales sale = salesService.getSaleById(id);
        return ResponseEntity.ok(sale);
    }

    @GetMapping
    public ResponseEntity<List<Sales>> getAllSales() {
        List<Sales> salesList = salesService.getAllSales();
        return ResponseEntity.ok(salesList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        salesService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
