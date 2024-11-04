package com.example.Automach.Service;

import com.example.Automach.DTO.InventoryDTO;
import com.example.Automach.entity.Inventory;
import com.example.Automach.entity.Product;
import com.example.Automach.entity.RawMaterial;
import com.example.Automach.entity.RawMaterialStock;
import com.example.Automach.repo.InventoryRepo;
import com.example.Automach.repo.ProductRepo;
import com.example.Automach.repo.RawMaterialStockRepo;
import com.example.Automach.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
@Service
public class InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo usersRepo;

    //Importing Raw material stock
    @Autowired
    private RawMaterialStockRepo rawMaterialStockRepo;

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


//    public InventoryDTO updateInventoryQuantity(Long id, Integer newQuantity) {
//        Inventory inventory = inventoryRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Inventory not found"));
//
//        // Update available quantity
//        inventory.setQuantity(newQuantity);
//        int required = inventory.getRequiredQuantity();
//        int required_initial=inventory.getRequiredQuantity();
//        // Scenario S1: Required <= Available
//        if (required <= newQuantity) {
//            inventory.setBlockedQuantity(inventory.getBlockedQuantity() + required);
//            inventory.setQuantity(newQuantity - required_initial);
//            inventory.setRequiredQuantity(0);
//        }
//        // Scenario S2: Required > Available
//        else {
//            inventory.setBlockedQuantity(inventory.getBlockedQuantity() + newQuantity);
//            inventory.setRequiredQuantity(required - newQuantity);
//            inventory.setQuantity(0);
//        }
//
//        inventory.setModifiedDate(new Date());
//        Inventory updatedInventory = inventoryRepo.save(inventory);
//        return convertToDTO(updatedInventory);
//    }

    @Transactional // Ensure atomicity
    public InventoryDTO updateInventoryQuantity(Long id, Integer newQuantity) {
        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        Product product = inventory.getProduct();
        int existingQuantity = inventory.getQuantity();
        System.out.println(existingQuantity);
        int quantityDifference = newQuantity - existingQuantity;


//        if (quantityDifference > 0) {
//            int reqQuant = inventory.getRequiredQuantity();
//
//            if (quantityDifference > reqQuant) {
//                inventory.setBlockedQuantity(inventory.getBlockedQuantity() + reqQuant);
//                inventory.setQuantity(inventory.getQuantity() + (quantityDifference - reqQuant));
//                inventory.setRequiredQuantity(0);
//            } else if (quantityDifference == reqQuant) {
//                inventory.setRequiredQuantity(0);
//                inventory.setBlockedQuantity(quantityDifference);
//            } else {
//                inventory.setRequiredQuantity(inventory.getRequiredQuantity() - quantityDifference);
//                inventory.setBlockedQuantity(inventory.getBlockedQuantity() + quantityDifference);
//
//            }
//        }

       //  Update available quantity
//        inventory.setQuantity(newQuantity);
        int required = inventory.getRequiredQuantity();
        int required_initial=inventory.getRequiredQuantity();
        // Scenario S1: Required <= Available
        if (required <= newQuantity) {
            inventory.setBlockedQuantity(inventory.getBlockedQuantity() + required);
            inventory.setQuantity(newQuantity - required_initial);
            inventory.setRequiredQuantity(0);
        }
        // Scenario S2: Required > Available
        else {
            inventory.setQuantity(0);
            inventory.setBlockedQuantity(inventory.getBlockedQuantity() + newQuantity);
            inventory.setRequiredQuantity(required - newQuantity);
//            inventory.setQuantity(0);
        }
        // If the quantityDifference is positive, we are adding more units, hence need more raw materials.
        if (quantityDifference > 0) {
            // Calculate required quantities for each raw material
            for (var productRawMaterial : product.getRawMaterials()) {
                RawMaterial rawMaterial = productRawMaterial.getRawMaterial();
                int requiredQuantity = productRawMaterial.getRawMaterialQuantity() * quantityDifference;

                // Fetch the current stock of the raw material
                RawMaterialStock rawMaterialStock = rawMaterialStockRepo.findByRawMaterialMaterialName(rawMaterial.getMaterialName())
                        .orElseThrow(() -> new RuntimeException("Raw material stock not found for: " + rawMaterial.getMaterialName()));

                // Check if sufficient raw material is available
                if (rawMaterialStock.getQuantity() < requiredQuantity) {
                    throw new RuntimeException("Insufficient stock for raw material: " + rawMaterial.getMaterialName());
                }
            }

            // Update raw material stocks (decrease quantities)
            for (var productRawMaterial : product.getRawMaterials()) {
                RawMaterial rawMaterial = productRawMaterial.getRawMaterial();
                int requiredQuantity = productRawMaterial.getRawMaterialQuantity() * quantityDifference;

                // Fetch the current stock of the raw material
                RawMaterialStock rawMaterialStock = rawMaterialStockRepo.findByRawMaterialMaterialName(rawMaterial.getMaterialName())
                        .orElseThrow(() -> new RuntimeException("Raw material stock not found for: " + rawMaterial.getMaterialName()));

                // Decrease the raw material stock quantity
                rawMaterialStock.setQuantity(rawMaterialStock.getQuantity() - requiredQuantity);
                rawMaterialStockRepo.save(rawMaterialStock);
            }
        }


        // Update the product inventory
//        inventory.setQuantity(newQuantity);
        inventory.setModifiedDate(new Date());
        Inventory updatedInventory = inventoryRepo.save(inventory);

        return convertToDTO(updatedInventory);
    }




    private InventoryDTO convertToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setInventoryId(inventory.getInventoryId());
        dto.setProductId(inventory.getProduct().getProdId());
        dto.setQuantity(inventory.getQuantity());
        dto.setSku(inventory.getSku());
        dto.setBlockedQuantity(inventory.getBlockedQuantity());
        dto.setRequiredQuantity(inventory.getRequiredQuantity());
        dto.setModifiedDate(inventory.getModifiedDate());
        return dto;
    }

    public void releaseInventoryQuantities(Long productId, int quantityToRelease) {
        Inventory inventory = inventoryRepo.findByProductProdId(productId);

        if (inventory == null) {
            throw new RuntimeException("No inventory found for product ID: " + productId);
        }

        if (inventory.getBlockedQuantity() >= quantityToRelease) {
            inventory.setBlockedQuantity(inventory.getBlockedQuantity() - quantityToRelease);
        }

//        if (inventory.getQuantity() > 0) {
//            if (inventory.getBlockedQuantity() == 0) {
//                inventory.setBlockedQuantity(0);
//            } else if (inventory.getBlockedQuantity() >= quantityToRelease) {
//                inventory.setBlockedQuantity(inventory.getBlockedQuantity() - quantityToRelease);
//            }
//        }
//        else if (inventory.getQuantity() == 0 ) {
//            if(inventory.getBlockedQuantity() >=quantityToRelease && inventory.getRequiredQuantity()==0){
//                inventory.setBlockedQuantity(inventory.getBlockedQuantity() - quantityToRelease);
//            }
//            else if (inventory.getBlockedQuantity() == quantityToRelease) {
//                inventory.setBlockedQuantity(0);
//            }
//            else if (inventory.getRequiredQuantity() > 0 ) {
//                if(inventory.getRequiredQuantity() < quantityToRelease ){
//                    int initial_required =inventory.getRequiredQuantity();
//                    inventory.setRequiredQuantity(0);
//                    int toBlock = quantityToRelease - initial_required;
//                    inventory.setBlockedQuantity(inventory.getBlockedQuantity() - toBlock);
//                }
//                else if (inventory.getRequiredQuantity() > quantityToRelease) {
//                    inventory.setRequiredQuantity(inventory.getRequiredQuantity() - quantityToRelease);
//                }
//            }
//        }

        inventory.setModifiedDate(new Date());
        inventoryRepo.save(inventory);

        System.out.println("Updated Inventory: " + inventory);
    }

    public void updateInventoryQuantities(Long productId, int oldQuantity, int newQuantity) {
        Inventory inventory = inventoryRepo.findByProductProdId(productId);

        if (inventory == null) {
            throw new RuntimeException("No inventory found for product ID: " + productId);
        }

        int quantityDifference = newQuantity - oldQuantity;

        if (quantityDifference > 0) {
            if (inventory.getQuantity() > 0) {
                if (inventory.getQuantity() >= quantityDifference) {
                    inventory.setQuantity(inventory.getQuantity() - quantityDifference);
                    inventory.setBlockedQuantity(inventory.getBlockedQuantity() + quantityDifference);
                } else {
                    int existingQuantity = inventory.getQuantity();
                    inventory.setQuantity(0);
                    inventory.setBlockedQuantity(inventory.getBlockedQuantity() + existingQuantity);
                    inventory.setRequiredQuantity(inventory.getRequiredQuantity() + quantityDifference - existingQuantity);
                }
            } else if (inventory.getQuantity() == 0) {
                inventory.setRequiredQuantity(inventory.getRequiredQuantity() + quantityDifference);
            }
        } else if (quantityDifference < 0) {
            quantityDifference = Math.abs(quantityDifference);
            if (inventory.getRequiredQuantity() > 0) {
               int required_initial=inventory.getRequiredQuantity();
                if (inventory.getRequiredQuantity() < quantityDifference) {
                    inventory.setBlockedQuantity(inventory.getBlockedQuantity() + inventory.getRequiredQuantity() - quantityDifference);
                    inventory.setRequiredQuantity(0);
                    inventory.setQuantity(quantityDifference-required_initial);
                }
                else {
                    inventory.setRequiredQuantity(inventory.getRequiredQuantity() - quantityDifference);
                }
            } else if (inventory.getRequiredQuantity() == 0 && inventory.getBlockedQuantity() > 0) {
                inventory.setBlockedQuantity(inventory.getBlockedQuantity() - quantityDifference);
                inventory.setQuantity(inventory.getQuantity() + quantityDifference);
            }
        }

        inventory.setModifiedDate(new Date());
        inventoryRepo.save(inventory);
    }

    public void updateBlockedQuantityWithRequirement(Long productId, int quantityToBlock) {
        Inventory inventory = inventoryRepo.findByProductProdId(productId);

        if (inventory == null) {
            throw new RuntimeException("No inventory found for product ID: " + productId);
        }

        System.out.println("Updating inventory for product ID: " + productId);
        System.out.println("Current Quantity: " + inventory.getQuantity());
        System.out.println("Requested Quantity to Block: " + quantityToBlock);

        if (inventory.getQuantity() >= quantityToBlock) {
            inventory.setQuantity(inventory.getQuantity() - quantityToBlock);
            inventory.setBlockedQuantity(inventory.getBlockedQuantity() + quantityToBlock);
            inventory.setRequiredQuantity(0);
        } else {
            int existingQuantity = inventory.getQuantity();
            inventory.setQuantity(0);
            inventory.setBlockedQuantity(inventory.getBlockedQuantity() + existingQuantity);
            inventory.setRequiredQuantity(inventory.getRequiredQuantity()+quantityToBlock);
        }

        inventory.setModifiedDate(new Date());
        inventoryRepo.save(inventory);

        System.out.println("Updated Inventory: " + inventory);
    }


    public void releaseBlockedQuantity(Long productId, int quantityToRelease) {
        try {
            Inventory inventory = inventoryRepo.findByProductProdId(productId);
            if (inventory != null) {
                if (inventory.getBlockedQuantity() >= quantityToRelease) {
                    inventory.setBlockedQuantity(inventory.getBlockedQuantity() - quantityToRelease);
                }
                else {
                    int remainingQuantity = quantityToRelease - inventory.getBlockedQuantity();
                    inventory.setBlockedQuantity(0);
                    inventory.setRequiredQuantity(inventory.getRequiredQuantity() - remainingQuantity);
                }

                inventoryRepo.save(inventory);
            }
        } catch (Exception e) {
            throw new RuntimeException("No inventory found for product ID: " + productId);
        }
    }

    public List<InventoryDTO> getInventoryByProductName(String productName) {
        List<Inventory> inventoryList = inventoryRepo.findByProductProdNameIgnoreCase(productName);
        return inventoryList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }




    private String generateSKU(Long productId) {
        return productId + String.format("%05d", inventoryRepo.count() + 1);
    }
}
