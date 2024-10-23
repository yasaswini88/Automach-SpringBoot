package com.example.Automach.Service;

import com.example.Automach.DTO.InventoryDTO;
import com.example.Automach.entity.Inventory;
import com.example.Automach.entity.Product;
import com.example.Automach.repo.InventoryRepo;
import com.example.Automach.repo.ProductRepo;
import com.example.Automach.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo usersRepo;

    public InventoryDTO addOrUpdateInventory(InventoryDTO inventoryDTO) {
        Product product = productRepo.findById(inventoryDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(inventoryDTO.getQuantity());
        inventory.setSku(generateSKU(product.getProdId()));
//        inventory.setStatus(inventoryDTO.getStatus());
        inventory.setModifiedDate(new Date());

        Inventory savedInventory = inventoryRepo.save(inventory);
        return convertToDTO(savedInventory);
    }



    // Fetch all inventory items
    public List<InventoryDTO> getAllInventory() {
        List<Inventory> inventoryList = inventoryRepo.findAll();
        return inventoryList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public InventoryDTO getInventoryById(Long id) {
        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        return convertToDTO(inventory);
    }

    public List<InventoryDTO> getInventoryByProductId(Long productId) {
        Inventory inventory = inventoryRepo.findByProductProdId(productId);
        List<InventoryDTO> list = new ArrayList<>();
        list.add(this.convertToDTO(inventory));
        return list;
    }

    public InventoryDTO getInventoryBySku(String sku) {
        Inventory inventory = inventoryRepo.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Inventory not found with SKU: " + sku));
        return convertToDTO(inventory);
    }

    public InventoryDTO updateInventoryStatus(Long id, String status) {
        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
//        inventory.setStatus(status);
        inventory.setModifiedDate(new Date());
        Inventory updatedInventory = inventoryRepo.save(inventory);
        return convertToDTO(updatedInventory);
    }

    public void deleteInventoryById(Long id) {
        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventoryRepo.delete(inventory);
    }

    private InventoryDTO convertToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setInventoryId(inventory.getInventoryId());
        dto.setProductId(inventory.getProduct().getProdId());
        dto.setQuantity(inventory.getQuantity());
        dto.setSku(inventory.getSku());
        dto.setBlockedQuantity(inventory.getBlockedQuantity());
        dto.setRequiredQuantity(inventory.getRequiredQuantity());
//        dto.setStatus(inventory.getStatus());
        dto.setModifiedDate(inventory.getModifiedDate());
        return dto;
    }

    public InventoryDTO updateInventoryQuantity(Long id, Integer quantity) {
        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        inventory.setQuantity(quantity);
        inventory.setModifiedDate(new Date());

        Inventory updatedInventory = inventoryRepo.save(inventory);
        return convertToDTO(updatedInventory);
    }



    public void updateBlockedQuantity(Long productId, int quantityToBlock) {
        try {
            Inventory inventory = inventoryRepo.findByProductProdId(productId);

            if (inventory != null) {
                // Reduce available quantity
                if (inventory.getQuantity() >= quantityToBlock) {
                    inventory.setQuantity(inventory.getQuantity() - quantityToBlock);
                    inventory.setBlockedQuantity(inventory.getBlockedQuantity() + quantityToBlock);
                } else {
                    int requiredQuantity = quantityToBlock - inventory.getQuantity();
                    int existingQuantity = inventory.getQuantity();
                    inventory.setQuantity(0);
                    inventory.setBlockedQuantity(inventory.getBlockedQuantity() + existingQuantity);
                    inventory.setRequiredQuantity(requiredQuantity);  // Record any shortage
                }

                inventoryRepo.save(inventory);
            }
        } catch (Exception e) {
            throw new RuntimeException("No inventory found for product ID: " + productId);
        }
    }




    public void releaseBlockedQuantity(Long productId, int quantityToRelease) {

        try {
            Inventory inventory = inventoryRepo.findByProductProdId(productId);
            if (inventory != null) {
                if (inventory.getBlockedQuantity() >= quantityToRelease) {
                    inventory.setBlockedQuantity(inventory.getBlockedQuantity() - quantityToRelease);
                }
//                else {
//                    int remainingQuantity = quantityToRelease - inventory.getBlockedQuantity();
//                    inventory.setBlockedQuantity(0);
//                    inventory.setRequiredQuantity(inventory.getRequiredQuantity() - remainingQuantity);
//                }

                inventoryRepo.save(inventory);
            }
        } catch (Exception e) {
            throw new RuntimeException("No inventory found for product ID: " + productId);
        }
    }

    private String generateSKU(Long productId) {
        return productId + String.format("%05d", inventoryRepo.count() + 1);
    }
}
