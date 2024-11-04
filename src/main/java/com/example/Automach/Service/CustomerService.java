package com.example.Automach.Service;

import com.example.Automach.DTO.CustomerDTO;
import com.example.Automach.entity.Customer;
import com.example.Automach.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setAddressLine1(customerDTO.getAddressLine1());
        customer.setAddressLine2(customerDTO.getAddressLine2());
        customer.setCity(customerDTO.getCity());
        customer.setState(customerDTO.getState());
        customer.setPostalCode(customerDTO.getPostalCode());

        return customerRepo.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerRepo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setAddressLine1(customerDTO.getAddressLine1());
        customer.setAddressLine2(customerDTO.getAddressLine2());
        customer.setCity(customerDTO.getCity());
        customer.setState(customerDTO.getState());
        customer.setPostalCode(customerDTO.getPostalCode());

        return customerRepo.save(customer);
    }


    public void deleteCustomer(Long id) {
        if (customerRepo.existsById(id)) {
            customerRepo.deleteById(id);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }
}
