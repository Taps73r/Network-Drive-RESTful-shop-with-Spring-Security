package com.example.demo.service;

import com.example.demo.DTO.CustomerDTO;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {this.customerRepository = customerRepository;}

    public Page<CustomerDTO> getCustomersWithPagination(int page, int size) {
        Page<Customer> customerPage = customerRepository.findAll(PageRequest.of(page, size));
        return customerPage.map(CustomerDTO::fromCustomer);
    }
    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));

        customerRepository.delete(existingCustomer);
    }


}