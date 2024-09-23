package com.example.Automach.Service;

import com.example.Automach.DTO.RawMaterialOrderStatusDTO;
import com.example.Automach.entity.RawMaterial;
import com.example.Automach.entity.RawMaterialOrderStatus;
import com.example.Automach.entity.Users;
import com.example.Automach.repo.RawMaterialOrderStatusRepo;
import com.example.Automach.repo.RawMaterialRepo;
import com.example.Automach.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RawMaterialOrderStatusService {

    @Autowired
    private RawMaterialOrderStatusRepo repository;

    @Autowired
    private RawMaterialRepo rawMaterialRepository;

    @Autowired
    private UserRepo usersRepository;

    public RawMaterialOrderStatusDTO createOrder(RawMaterialOrderStatusDTO dto) {
        RawMaterialOrderStatus orderStatus = new RawMaterialOrderStatus();
        RawMaterial rawMaterial = findRawMaterialById(dto.getRawMaterialId());
        orderStatus.setRawMaterial(rawMaterial);
        orderStatus.setRawMaterialQuantity(dto.getRawMaterialQuantity());
        orderStatus.setSupplierName(dto.getSupplierName());
        orderStatus.setStatus(dto.getStatus());
        orderStatus.setTrackingInfo(dto.getTrackingInfo());
        orderStatus.setNotes(dto.getNotes());
        orderStatus.setCreatedBy(findUserById(dto.getCreatedBy()));
        orderStatus.setCreatedDate(dto.getCreatedDate());

        // Set updatedBy and updatedDate to null during creation
        orderStatus.setUpdatedBy(null);
        orderStatus.setUpdatedDate(null);

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
            orderStatus.setTrackingInfo(dto.getTrackingInfo());
            orderStatus.setNotes(dto.getNotes());
            orderStatus.setUpdatedBy(findUserById(dto.getUpdatedBy()));
            orderStatus.setUpdatedDate(dto.getUpdatedDate());

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

    public RawMaterialOrderStatusDTO mapToDTO(RawMaterialOrderStatus orderStatus) {
        RawMaterialOrderStatusDTO dto = new RawMaterialOrderStatusDTO();
        dto.setOrderId(orderStatus.getOrderId());
        dto.setRawMaterialId(orderStatus.getRawMaterial().getId());
        dto.setRawMaterialName(orderStatus.getRawMaterial().getMaterialName()); // Set the name here
        dto.setRawMaterialQuantity(orderStatus.getRawMaterialQuantity());
        dto.setSupplierName(orderStatus.getSupplierName());
        dto.setStatus(orderStatus.getStatus());
        dto.setTrackingInfo(orderStatus.getTrackingInfo());
        dto.setNotes(orderStatus.getNotes());
        dto.setCreatedBy(orderStatus.getCreatedBy().getUserId());
        dto.setUpdatedBy(orderStatus.getUpdatedBy() != null ? orderStatus.getUpdatedBy().getUserId() : null);
        dto.setCreatedDate(orderStatus.getCreatedDate());
        dto.setUpdatedDate(orderStatus.getUpdatedDate());
        return dto;
    }

}
