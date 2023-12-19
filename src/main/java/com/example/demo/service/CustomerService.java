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
    public CustomerDTO login(String email, String password) {
        Customer customer = customerRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        return CustomerDTO.fromCustomer(customer);
    }
    @Transactional
    public void addNewCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));

        customerRepository.delete(existingCustomer);
    }

    public void updateCustomer(Long customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        if (updatedCustomer.getPassword() != null) {
            existingCustomer.setPassword(updatedCustomer.getPassword());
        }

        customerRepository.save(existingCustomer);
    }

}