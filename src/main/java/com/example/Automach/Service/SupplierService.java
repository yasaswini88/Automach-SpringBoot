package com.example.Automach.Service;
import com.example.Automach.DTO.SupplierDTO;
import com.example.Automach.entity.Supplier;
import com.example.Automach.repo.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepo supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public Supplier addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, SupplierDTO supplierDTO) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow();
        supplier.setName(supplierDTO.getName());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setPhone(supplierDTO.getPhone());
        supplier.setAddressLine1(supplierDTO.getAddressLine1());
        supplier.setAddressLine2(supplierDTO.getAddressLine2());
        supplier.setCity(supplierDTO.getCity());
        supplier.setState(supplierDTO.getState());
        supplier.setPostalCode(supplierDTO.getPostalCode());
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
