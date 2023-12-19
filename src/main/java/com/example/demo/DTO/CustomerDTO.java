package com.example.demo.DTO;

import com.example.demo.model.Customer;

import java.util.Objects;

public record CustomerDTO(Long id, String name, String email) {
    public CustomerDTO {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(email);
    }

    public static CustomerDTO fromCustomer(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail());
    }
}
