package com.example.Automach.Service;

import com.example.Automach.DTO.SalesDTO;
import com.example.Automach.DTO.SalesUpdateDTO;
import com.example.Automach.entity.Sales;
import com.example.Automach.entity.Product;
import com.example.Automach.entity.Users;
import com.example.Automach.repo.SalesRepo;
import com.example.Automach.repo.ProductRepo;
import com.example.Automach.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SalesService {

    @Autowired
    private SalesRepo salesRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

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
    public Sales updateSale(Long saleId, SalesUpdateDTO salesUpdateDTO) {
        Sales existingSale = salesRepo.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found"));

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
        }
        existingSale.setTotalPrice(totalPrice);

        // Recalculate final price with discount
        Double discount = salesUpdateDTO.getDiscountPercent();
        existingSale.setDiscountPercent(discount);

        Double finalPrice = totalPrice - (totalPrice * discount / 100);
        existingSale.setFinalPrice(finalPrice);

        existingSale.setOrderStatus(salesUpdateDTO.getOrderStatus());
        existingSale.setOrderDeliveryDate(salesUpdateDTO.getOrderDeliveryDate());

        // Retrieve the user who updated the sale
        Users updatedBy = userRepo.findById(salesUpdateDTO.getUpdatedUserId()).orElse(null);
        existingSale.setUpdatedBy(updatedBy);
        existingSale.setUpdatedDate(salesUpdateDTO.getUpdatedDate());

        // Save and return the updated sale entity
        return salesRepo.save(existingSale);
    }

    public Sales getSaleById(Long saleId) {
        return salesRepo.findById(saleId).orElse(null);
    }

    public List<Sales> getAllSales() {
        return salesRepo.findAll();
    }

    public void deleteSale(Long saleId) {
        salesRepo.deleteById(saleId);
    }
}
