package com.example.Automach.controller;

import com.example.Automach.DTO.RawMaterialOrderStatusDTO;
import com.example.Automach.Service.RawMaterialOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class RawMaterialOrderStatusController {

    @Autowired
    private RawMaterialOrderStatusService service;

    @PostMapping
    public ResponseEntity<RawMaterialOrderStatusDTO> createOrder(@RequestBody RawMaterialOrderStatusDTO dto) {
        RawMaterialOrderStatusDTO createdOrder = service.createOrder(dto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<RawMaterialOrderStatusDTO> updateOrder(@PathVariable Long orderId, @RequestBody RawMaterialOrderStatusDTO dto) {
        RawMaterialOrderStatusDTO updatedOrder = service.updateOrder(orderId, dto);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<RawMaterialOrderStatusDTO> getOrderById(@PathVariable Long orderId) {
        RawMaterialOrderStatusDTO order = service.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<RawMaterialOrderStatusDTO>> getAllOrders() {
//        List<RawMaterialOrderStatusDTO> orders = service.getAllOrders().stream().map(service::mapToDTO).toList();
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<List<RawMaterialOrderStatusDTO>> getOrdersByStatus(@RequestParam(value = "status", required = false) String status) {
        List<RawMaterialOrderStatusDTO> orders;
        if (status != null && !status.isEmpty()) {
            orders = service.getOrdersByStatus(status).stream().map(service::mapToDTO).toList();
        } else {
            orders = service.getAllOrders().stream().map(service::mapToDTO).toList();
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // get order status of raw materials based on suppliers
    @GetMapping("/{rawMaterialName}/{supplierName}/status")
    public ResponseEntity<List<RawMaterialOrderStatusDTO>> getOrderStatusByRawMaterialAndSupplier(
            @PathVariable String rawMaterialName,
            @PathVariable String supplierName) {
        List<RawMaterialOrderStatusDTO> orders = service.getOrderStatusByRawMaterialAndSupplier(rawMaterialName, supplierName);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        service.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
