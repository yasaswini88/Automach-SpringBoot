package com.example.Automach.controller;

import com.example.Automach.DTO.RawMaterialCountDTO;
import com.example.Automach.DTO.SalesDTO;
import com.example.Automach.DTO.SalesUpdateDTO;
import com.example.Automach.Service.SalesService;
import com.example.Automach.entity.Product;
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
//        System.out.println(salesUpdateDTO);
        Sales updatedSale = salesService.updateSale(id, salesUpdateDTO);
        return ResponseEntity.ok(updatedSale);
    }

    // PUT mapping for updating the status of a sale
    @PutMapping("/{saleId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long saleId, @RequestParam String status) {
        salesService.updateSaleStatus(saleId, status);
        return ResponseEntity.ok("Order status updated.");
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Sales> getSaleById(@PathVariable Long id) {
//        Sales sale = salesService.getSaleById(id);
//        return ResponseEntity.ok(sale);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Sales> getSaleById(@PathVariable Long id) {
        Sales sale = salesService.getSaleById(id);
        return ResponseEntity.ok(sale);
    }

    @GetMapping("/{id}/ready-to-ship")
    public ResponseEntity<Boolean> checkReadyToShip(@PathVariable Long id) {
        return ResponseEntity.ok(salesService.checkReadyToShip(id));
    }

    @GetMapping
    public ResponseEntity<List<Sales>> getAllSales() {
        List<Sales> salesList = salesService.getAllSales();
        return ResponseEntity.ok(salesList);
    }

    @GetMapping("/most-ordered-products")
    public ResponseEntity<List<Product>> getMostOrderedProducts() {
        List<Product> mostOrderedProducts = salesService.getMostOrderedProducts();
        return ResponseEntity.ok(mostOrderedProducts);
    }

    // New endpoint to get the top raw materials
    @GetMapping("/top-raw-materials")
    public ResponseEntity<List<RawMaterialCountDTO>> getTopRawMaterials() {
        List<RawMaterialCountDTO> topRawMaterials = salesService.getTopRawMaterials();
        return ResponseEntity.ok(topRawMaterials);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        salesService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
