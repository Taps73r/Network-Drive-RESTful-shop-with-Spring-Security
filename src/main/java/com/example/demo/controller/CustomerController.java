package com.example.demo.controller;

import com.example.demo.DTO.CustomerDTO;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get Customers with Pagination
    // тров ерор
    @GetMapping
    @Cacheable(value = "customers", key = "{#page, #size}")
    public Page<CustomerDTO> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (size > 20) {
            throw new IllegalArgumentException("Size cannot exceed 20");
        }
        else {
            return customerService.getCustomersWithPagination(page, size);
        }
    }


    // Delete Customer
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}