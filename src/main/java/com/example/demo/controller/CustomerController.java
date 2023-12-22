package com.example.demo.controller;

import com.example.demo.DTO.CustomerDTO;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get Customers with Pagination

    @GetMapping
    @Cacheable(value = "customers", key = "{#page, #size}")
    public Page<CustomerDTO> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return customerService.getCustomersWithPagination(page, size);
    }

    // Create Customer
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        List<String> validationErrors = validateCustomer(customer);
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrors);
        }
        else {
            customerService.addNewCustomer(customer);
            CustomerDTO responseDTO = CustomerDTO.fromCustomer(customer);
            return ResponseEntity.ok(responseDTO);
        }
    }

    private List<String> validateCustomer(Customer customer) {
        List<String> errors = new ArrayList<>();
        if (customer.getName() == null || customer.getName().length() < 4 || customer.getName().length() > 15) {
            errors.add("Name must be between 4 and 15 characters");
        }
        if (customer.getEmail() == null || customer.getEmail().length() < 4 || customer.getEmail().length() > 24) {
            errors.add("Email must be between 4 and 24 characters");
        }
        if (customer.getPassword() == null || customer.getPassword().length() < 4 || customer.getPassword().length() > 15) {
            errors.add("Password must be between 4 and 15 characters");
        }
        return errors;
    }

    // Delete Customer
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    // Update Customer
    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody Customer updatedCustomer) {
        List<String> validationErrors = validateCustomer(updatedCustomer);
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrors);
        }
        else {
            customerService.updateCustomer(customerId, updatedCustomer);
            CustomerDTO responseDTO = CustomerDTO.fromCustomer(updatedCustomer);
            return ResponseEntity.ok(responseDTO);
        }
    }

}