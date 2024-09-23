package com.example.Automach.controller;



import com.example.Automach.DTO.SupplierDTO;
import com.example.Automach.Service.SupplierService;
import com.example.Automach.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public Optional<Supplier> getSupplierById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping
    public Supplier addSupplier(@RequestBody SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setPhone(supplierDTO.getPhone());
        supplier.setAddressLine1(supplierDTO.getAddressLine1());
        supplier.setAddressLine2(supplierDTO.getAddressLine2());
        supplier.setCity(supplierDTO.getCity());
        supplier.setState(supplierDTO.getState());
        supplier.setPostalCode(supplierDTO.getPostalCode());
        return supplierService.addSupplier(supplier);
    }

    @PutMapping("/{id}")
    public Supplier updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO) {
        return supplierService.updateSupplier(id, supplierDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }
}
