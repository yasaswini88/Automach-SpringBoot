package com.example.Automach.Service;

import com.example.Automach.DTO.RawMaterialOrderStatusDTO;
import com.example.Automach.entity.*;
import com.example.Automach.repo.RawMaterialOrderStatusRepo;
import com.example.Automach.repo.RawMaterialRepo;
import com.example.Automach.repo.SupplierRepo;
import com.example.Automach.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RawMaterialOrderStatusService {

    @Autowired
    private RawMaterialOrderStatusRepo repository;

    @Autowired
    private RawMaterialRepo rawMaterialRepository;

    @Autowired
    private RawMaterialStockService rawMaterialStockService;

    @Autowired
    private UserRepo usersRepository;

    @Autowired
    private SupplierRepo supplierRepo;

    // New method to find suppliers by raw material in orders
    public List<Supplier> getSuppliersByRawMaterial(Long rawMaterialId) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(rawMaterialId)
                .orElseThrow(() -> new RuntimeException("Raw material not found with id: " + rawMaterialId));
        return supplierRepo.findByRawMaterialsContains(rawMaterial);
    }
    public RawMaterialOrderStatusDTO createOrder(RawMaterialOrderStatusDTO dto) {
        RawMaterialOrderStatus orderStatus = new RawMaterialOrderStatus();

        // Fetch the raw material by its ID
        RawMaterial rawMaterial = findRawMaterialById(dto.getRawMaterialId());
        orderStatus.setRawMaterial(rawMaterial);

        // Fetch the supplier by its ID and set the supplier name
        Supplier supplier = supplierRepo.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + dto.getSupplierId()));
        orderStatus.setSupplierName(supplier.getName()); // Set the supplier name

        // Set other order fields
        orderStatus.setRawMaterialQuantity(dto.getRawMaterialQuantity());
        orderStatus.setStatus(dto.getStatus());
//        orderStatus.setTrackingInfo(dto.getTrackingInfo());
        orderStatus.setNotes(dto.getNotes());
        orderStatus.setCreatedBy(findUserById(dto.getCreatedBy()));
        orderStatus.setCreatedDate(dto.getCreatedDate());

        // Save the order and return the DTO
        RawMaterialOrderStatus savedOrderStatus = repository.save(orderStatus);
        return mapToDTO(savedOrderStatus);
    }




    public RawMaterialOrderStatusDTO updateOrder(Long orderId, RawMaterialOrderStatusDTO dto) {
        Optional<RawMaterialOrderStatus> optionalOrderStatus = repository.findById(orderId);
        if (optionalOrderStatus.isPresent()) {
            RawMaterialOrderStatus orderStatus = optionalOrderStatus.get();
            RawMaterial rawMaterial = findRawMaterialById(dto.getRawMaterialId());
            orderStatus.setRawMaterial(rawMaterial);
            orderStatus.setRawMaterialQuantity(dto.getRawMaterialQuantity());
            orderStatus.setSupplierName(dto.getSupplierName());
            orderStatus.setStatus(dto.getStatus());
//            orderStatus.setTrackingInfo(dto.getTrackingInfo());
            orderStatus.setNotes(dto.getNotes());
            orderStatus.setUpdatedBy(findUserById(dto.getUpdatedBy()));
            orderStatus.setUpdatedDate(dto.getUpdatedDate());

            // Check if the status is being updated to 'Delivered'
            if ("Delivered".equals(dto.getStatus())) {
                RawMaterialStock rawMaterialStock = rawMaterialStockService.getRawMaterialStockByMaterialName(rawMaterial.getMaterialName())
                        .orElseThrow(() -> new RuntimeException("RawMaterialStock not found"));
                // Increase the stock quantity
                rawMaterialStock.setQuantity(rawMaterialStock.getQuantity() + dto.getRawMaterialQuantity());
                rawMaterialStockService.saveRawMaterialStock(rawMaterialStock);
            }

            RawMaterialOrderStatus updatedOrderStatus = repository.save(orderStatus);
            return mapToDTO(updatedOrderStatus);
        } else {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }
    public RawMaterialOrderStatusDTO getOrderById(Long orderId) {
        Optional<RawMaterialOrderStatus> optionalOrderStatus = repository.findById(orderId);
        if (optionalOrderStatus.isPresent()) {
            return mapToDTO(optionalOrderStatus.get());
        } else {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }

    public List<RawMaterialOrderStatus> getAllOrders() {
        return repository.findAll();
    }

    public void deleteOrder(Long orderId) {
        repository.deleteById(orderId);
    }

    private RawMaterial findRawMaterialById(Long id) {
        return rawMaterialRepository.findById(id).orElseThrow(() -> new RuntimeException("RawMaterial not found with ID: " + id));
    }

    private Users findUserById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // Adding new method for getting orders which are only delivered
    // New method to get orders by status
    public List<RawMaterialOrderStatus> getOrdersByStatus(String status) {
        return repository.findOrdersByStatus(status);
    }

    // METHOD to get the order status of raw materials with a specific Supplier

    public List<RawMaterialOrderStatusDTO> getOrderStatusByRawMaterialAndSupplier(String rawMaterialName, String supplierName) {
        List<RawMaterialOrderStatus> orders = repository.findOrderStatusByRawMaterialAndSupplier(rawMaterialName, supplierName);
        return orders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }



    public RawMaterialOrderStatusDTO mapToDTO(RawMaterialOrderStatus orderStatus) {
        RawMaterialOrderStatusDTO dto = new RawMaterialOrderStatusDTO();
        dto.setOrderId(orderStatus.getOrderId());
        dto.setRawMaterialId(orderStatus.getRawMaterial().getId());
        dto.setRawMaterialName(orderStatus.getRawMaterial().getMaterialName()); // Set the name here
        dto.setRawMaterialQuantity(orderStatus.getRawMaterialQuantity());
        dto.setSupplierName(orderStatus.getSupplierName());
        dto.setStatus(orderStatus.getStatus());
//        dto.setTrackingInfo(orderStatus.getTrackingInfo());
        dto.setNotes(orderStatus.getNotes());
        dto.setCreatedBy(orderStatus.getCreatedBy().getUserId());
        dto.setUpdatedBy(orderStatus.getUpdatedBy() != null ? orderStatus.getUpdatedBy().getUserId() : null);
        dto.setCreatedDate(orderStatus.getCreatedDate());
        dto.setUpdatedDate(orderStatus.getUpdatedDate());
        return dto;
    }

}
