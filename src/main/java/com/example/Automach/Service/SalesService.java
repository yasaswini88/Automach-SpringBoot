package com.example.Automach.Service;

import com.example.Automach.DTO.RawMaterialCountDTO;
import com.example.Automach.DTO.SalesDTO;
import com.example.Automach.DTO.SalesUpdateDTO;
import com.example.Automach.entity.*;
import com.example.Automach.repo.InventoryRepo;
import com.example.Automach.repo.SalesRepo;
import com.example.Automach.repo.ProductRepo;
import com.example.Automach.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalesService {

    @Autowired
    private SalesRepo salesRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private InventoryRepo inventoryRepo;

    // Method to create a new sale
    public Sales createSale(SalesDTO salesDTO) {

        Sales sale = new Sales();
        sale.setCustomerName(salesDTO.getCustomerName());
        sale.setOrderDecision(salesDTO.getOrderDecision());

        // Retrieve the products based on the product IDs
        List<Product> products = productRepo.findAllById(salesDTO.getProductIds());
        sale.setProducts(products);
        sale.setQuantities(salesDTO.getQuantities());

        // Calculate total price
        Double totalPrice = 0.0;
        for (int i = 0; i < products.size(); i++) {
            totalPrice += products.get(i).getPrice() * salesDTO.getQuantities().get(i);

            if (salesDTO.getOrderDecision().equalsIgnoreCase("confirmed")) {
                inventoryService.updateBlockedQuantityWithRequirement(salesDTO.getProductIds().get(i), salesDTO.getQuantities().get(i));
            }


        }
        sale.setTotalPrice(totalPrice);

        // Apply the discount to calculate the final price
        Double discount = salesDTO.getDiscountPercent();
        sale.setDiscountPercent(discount);

        Double finalPrice = totalPrice - (totalPrice * discount / 100);
        sale.setFinalPrice(finalPrice);

        sale.setOrderStatus(salesDTO.getOrderStatus());
        sale.setOrderCreatedDate(salesDTO.getOrderCreatedDate());

        // Retrieve the user who created the sale
        Users createdBy = userRepo.findById(salesDTO.getCreatedUserId()).orElse(null);
        sale.setCreatedBy(createdBy);

        sale.setOrderDeliveryDate(salesDTO.getOrderDeliveryDate());
        // Save and return the sale entity
        return salesRepo.save(sale);
    }


    // Method to update an existing sale
//    public Sales updateSale(Long saleId, SalesUpdateDTO salesUpdateDTO) {
//        Sales existingSale = salesRepo.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found"));
//
//        existingSale.setCustomerName(salesUpdateDTO.getCustomerName());
//        existingSale.setOrderDecision(salesUpdateDTO.getOrderDecision());
//
//        // Retrieve and update products
//        List<Product> products = productRepo.findAllById(salesUpdateDTO.getProductIds());
//        existingSale.setProducts(products);
//        existingSale.setQuantities(salesUpdateDTO.getQuantities());
//
//        // Recalculate total price
//        Double totalPrice = 0.0;
//        for (int i = 0; i < products.size(); i++) {
//            totalPrice += products.get(i).getPrice() * salesUpdateDTO.getQuantities().get(i);
//
//            if (salesUpdateDTO.getOrderStatus().equalsIgnoreCase("delivered")) {
//                inventoryService.releaseBlockedQuantity(salesUpdateDTO.getProductIds().get(i), salesUpdateDTO.getQuantities().get(i));
//            }
//        }
//        existingSale.setTotalPrice(totalPrice);
//
//        // Recalculate final price with discount
//        Double discount = salesUpdateDTO.getDiscountPercent();
//        existingSale.setDiscountPercent(discount);
//
//        Double finalPrice = totalPrice - (totalPrice * discount / 100);
//        existingSale.setFinalPrice(finalPrice);
//
//        existingSale.setOrderStatus(salesUpdateDTO.getOrderStatus());
//        existingSale.setOrderDeliveryDate(salesUpdateDTO.getOrderDeliveryDate());
//
//        // Release blocked quantities if the order is delivered
//        if ("Delivered".equals(salesUpdateDTO.getOrderStatus())) {
//            for (int i = 0; i < products.size(); i++) {
//                inventoryService.releaseBlockedQuantity(products.get(i).getProdId(), salesUpdateDTO.getQuantities().get(i));
//            }
//        }
//
//        // Retrieve the user who updated the sale
//        Users updatedBy = userRepo.findById(salesUpdateDTO.getUpdatedUserId()).orElse(null);
//        existingSale.setUpdatedBy(updatedBy);
//        existingSale.setUpdatedDate(salesUpdateDTO.getUpdatedDate());
//
//        // Save and return the updated sale entity
//        return salesRepo.save(existingSale);
//    }
    public boolean checkReadyToShip(Long saleId) {
        Sales existingSale = salesRepo.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found"));

        boolean readyToShip = true;

        if (existingSale != null) {
            for (int i = 0; i < existingSale.getProducts().size(); i++) {
                Inventory inventory = inventoryRepo.findByProductProdId(existingSale.getProducts().get(i).getProdId());

                int saleQuantity = existingSale.getQuantities().get(i);

                if (inventory.getBlockedQuantity() < saleQuantity) {
                    return false;
                }
            }
        }

        return readyToShip;
    }

    // Method to update an existing sale
    public Sales updateSale(Long saleId, SalesUpdateDTO salesUpdateDTO) {
        Sales existingSale = salesRepo.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found"));

        System.out.println("Existing Order Decision: " + existingSale.getOrderDecision());
        System.out.println("New Order Decision: " + salesUpdateDTO.getOrderDecision());
        System.out.println("Order Decision Changed to Confirmed: " + salesUpdateDTO.isOrderDecisionChangedToConfirmed());

        // Condition 1: If initially created with "Confirmed" OR
        // Condition 2: If `orderDecision` changes from "Quoted" to "Confirmed"
        boolean shouldUpdateInventory = "confirmed".equalsIgnoreCase(existingSale.getOrderDecision())
                || salesUpdateDTO.isOrderDecisionChangedToConfirmed();

        if (shouldUpdateInventory) {
            // Update inventory for confirmed orders similar to after creating a new order
            for (int i = 0; i < salesUpdateDTO.getProductIds().size(); i++) {
                Long productId = salesUpdateDTO.getProductIds().get(i);
                int newQuantity = salesUpdateDTO.getQuantities().get(i);
                int oldQuantity = salesUpdateDTO.getOldQuantities().get(i);
                boolean shouldUpdate = salesUpdateDTO.isOrderDecisionChangedToConfirmed() || ("confirmed".equalsIgnoreCase(existingSale.getOrderDecision()) && newQuantity != oldQuantity);
                if (shouldUpdate) {
                    int oldValue = ("confirmed".equalsIgnoreCase(existingSale.getOrderDecision()) && newQuantity != oldQuantity) ? oldQuantity : 0;
                    inventoryService.updateInventoryQuantities(productId, oldValue, newQuantity);
                }
            }
        }

        boolean shouldReleaseInventory = !existingSale.getOrderStatus().equalsIgnoreCase(salesUpdateDTO.getOrderStatus()) && "shipped".equalsIgnoreCase(salesUpdateDTO.getOrderStatus());

        if (shouldReleaseInventory) {
            for (int i = 0; i < salesUpdateDTO.getProductIds().size(); i++) {
                Long productId = salesUpdateDTO.getProductIds().get(i);
                int quantity = salesUpdateDTO.getQuantities().get(i);
                inventoryService.releaseInventoryQuantities(productId, quantity);
            }
        }

        existingSale.setCustomerName(salesUpdateDTO.getCustomerName());
        existingSale.setOrderDecision(salesUpdateDTO.getOrderDecision());

        // Retrieve and update products
        List<Product> products = productRepo.findAllById(salesUpdateDTO.getProductIds());
        existingSale.setProducts(products);
        existingSale.setQuantities(salesUpdateDTO.getQuantities());

        // Recalculate total price
        Double totalPrice = 0.0;
        for (int i = 0; i < products.size(); i++) {
            totalPrice += products.get(i).getPrice() * salesUpdateDTO.getQuantities().get(i);

//            if (salesUpdateDTO.getOrderStatus().equalsIgnoreCase("delivered")) {
//                inventoryService.releaseBlockedQuantity(salesUpdateDTO.getProductIds().get(i), salesUpdateDTO.getQuantities().get(i));
//            }
        }
        existingSale.setTotalPrice(totalPrice);

        // Recalculate final price with discount
        Double discount = salesUpdateDTO.getDiscountPercent();
        existingSale.setDiscountPercent(discount);

        Double finalPrice = totalPrice - (totalPrice * discount / 100);
        existingSale.setFinalPrice(finalPrice);

        existingSale.setOrderStatus(salesUpdateDTO.getOrderStatus());
        existingSale.setOrderDeliveryDate(salesUpdateDTO.getOrderDeliveryDate());

        // Release blocked quantities if the order is delivered
//        if ("Delivered".equalsIgnoreCase(salesUpdateDTO.getOrderStatus())) {
//            for (int i = 0; i < products.size(); i++) {
//                inventoryService.releaseBlockedQuantity(products.get(i).getProdId(), salesUpdateDTO.getQuantities().get(i));
//            }
//        }

        // Retrieve the user who updated the sale
        Users updatedBy = userRepo.findById(salesUpdateDTO.getUpdatedUserId()).orElse(null);
        existingSale.setUpdatedBy(updatedBy);
        existingSale.setUpdatedDate(salesUpdateDTO.getUpdatedDate());

        // Save and return the updated sale entity
        return salesRepo.save(existingSale);
    }


    public List<Sales> getAllSales() {
        return salesRepo.findAll();
    }


@Transactional
public void deleteSale(Long saleId) {
    Sales sale = salesRepo.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found"));

    // Remove associations with products without deleting the products
    sale.getProducts().clear();
    salesRepo.save(sale);

    // Now delete the sale
    salesRepo.deleteById(saleId);
}

    public List<Product> getMostOrderedProducts() {
        List<Sales> allSales = salesRepo.findAll();
        Map<Product, Integer> productCountMap = new HashMap<>();

        for (Sales sale : allSales) {
            List<Product> products = sale.getProducts();
            List<Integer> quantities = sale.getQuantities();
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                productCountMap.put(product, productCountMap.getOrDefault(product, 0) + quantities.get(i));
            }
        }

        // Sort by count in descending order and return the most ordered products
        return productCountMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Get the top 10 most commonly used raw materials
    @Transactional(readOnly = true)
    public List<RawMaterialCountDTO> getTopRawMaterials() {
        List<Sales> allSales = salesRepo.findAll();
        Map<RawMaterial, Integer> rawMaterialCountMap = new HashMap<>();

        for (Sales sale : allSales) {
            for (Product product : sale.getProducts()) {
                product.getRawMaterials().forEach(rawMaterialMapping -> {
                    RawMaterial rawMaterial = rawMaterialMapping.getRawMaterial();
                    rawMaterialCountMap.put(rawMaterial, rawMaterialCountMap.getOrDefault(rawMaterial, 0) + rawMaterialMapping.getRawMaterialQuantity());
                });
            }
        }

        // Sort by count in descending order and return the top 10
        return rawMaterialCountMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .map(entry -> new RawMaterialCountDTO(entry.getKey().getMaterialName(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public Sales getSaleById(Long id) {
        return salesRepo.findById(id).orElseThrow(() -> new RuntimeException("Sale not found"));
    }

    // Method to update the status of a sale and handle inventory changes
    public void updateSaleStatus(Long saleId, String status) {
        Sales existingSale = salesRepo.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found"));

        // If order status is set to "Delivered", release blocked quantities
        if ("Delivered".equalsIgnoreCase(status)) {
            List<Product> products = existingSale.getProducts();
            List<Integer> quantities = existingSale.getQuantities();

            for (int i = 0; i < products.size(); i++) {
                inventoryService.releaseBlockedQuantity(products.get(i).getProdId(), quantities.get(i));
            }
        }

        // Update the sale's order status
        existingSale.setOrderStatus(status);

        // Save the updated sale entity
        salesRepo.save(existingSale);
    }

}
