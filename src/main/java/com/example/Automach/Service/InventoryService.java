package com.example.Automach.Service;

import com.example.Automach.DTO.InventoryDTO;
import com.example.Automach.entity.Inventory;
import com.example.Automach.entity.Product;
import com.example.Automach.entity.Users;
import com.example.Automach.repo.InventoryRepo;
import com.example.Automach.repo.ProductRepo;
import com.example.Automach.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo usersRepo;

    public InventoryDTO addOrUpdateInventory(InventoryDTO inventoryDTO) {
        // Find Product by ID
        Product product = productRepo.findById(inventoryDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Find User by ID
        Users user = usersRepo.findById(inventoryDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(inventoryDTO.getQuantity());
        inventory.setSku(generateSKU(product.getProdId()));
        inventory.setStatus(inventoryDTO.getStatus());
        inventory.setUserModified(user);
        inventory.setModifiedDate(new Date());

        Inventory savedInventory = inventoryRepo.save(inventory);

        return convertToDTO(savedInventory);
    }

    private InventoryDTO convertToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setInventoryId(inventory.getInventoryId());
        dto.setProductId(inventory.getProduct().getProdId());
        dto.setQuantity(inventory.getQuantity());
        dto.setSku(inventory.getSku());
        dto.setStatus(inventory.getStatus());
        dto.setModifiedDate(inventory.getModifiedDate());

        if (inventory.getUserModified() != null) {
            dto.setUserId(inventory.getUserModified().getUserId());
        }

        return dto;
    }

    private String generateSKU(Long productId) {
        // Implement SKU generation logic (e.g., prodId + counter)
        return productId + String.format("%05d", inventoryRepo.count() + 1);
    }
}
