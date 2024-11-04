package com.example.Automach.Service;
import com.example.Automach.DTO.SupplierDTO;
import com.example.Automach.entity.RawMaterial;
import com.example.Automach.entity.Supplier;
import com.example.Automach.repo.RawMaterialRepo;
import com.example.Automach.repo.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepo supplierRepository;

    @Autowired
    private RawMaterialRepo rawMaterialRepo;

    // New method to assign raw materials to a supplier
    public Supplier addRawMaterialsToSupplier(Long supplierId, Set<Long> rawMaterialIds) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow();
        List<RawMaterial> rawMaterialsList = rawMaterialRepo.findAllById(rawMaterialIds);
        // Convert Iterable to Set
        Set<RawMaterial> rawMaterials = new HashSet<>(rawMaterialsList);
        supplier.setRawMaterials(rawMaterials);
        return supplierRepository.save(supplier);
    }

    // New method to find suppliers by raw material
    public List<Supplier> findSuppliersByRawMaterial(Long rawMaterialId) {
        RawMaterial rawMaterial = rawMaterialRepo.findById(rawMaterialId).orElseThrow();
        return supplierRepository.findByRawMaterialsContains(rawMaterial);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public Optional<Supplier> getSupplierByName(String name) {
        return supplierRepository.findByNameIgnoreCase(name);
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

    public Supplier addSupplier(Supplier supplier, Set<Long> rawMaterialIds) {
        Set<RawMaterial> rawMaterials = new HashSet<>(rawMaterialRepo.findAllById(rawMaterialIds));
        supplier.setRawMaterials(rawMaterials);
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplierWithRawMaterials(Long id, SupplierDTO supplierDTO, Set<Long> rawMaterialIds) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow();
        supplier.setName(supplierDTO.getName());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setPhone(supplierDTO.getPhone());
        supplier.setAddressLine1(supplierDTO.getAddressLine1());
        supplier.setAddressLine2(supplierDTO.getAddressLine2());
        supplier.setCity(supplierDTO.getCity());
        supplier.setState(supplierDTO.getState());
        supplier.setPostalCode(supplierDTO.getPostalCode());

        // Update raw materials
        Set<RawMaterial> rawMaterials = new HashSet<>(rawMaterialRepo.findAllById(rawMaterialIds));
        supplier.setRawMaterials(rawMaterials);
        return supplierRepository.save(supplier);
    }

}
