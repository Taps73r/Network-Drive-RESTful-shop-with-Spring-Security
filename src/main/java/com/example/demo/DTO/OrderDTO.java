package com.example.demo.DTO;

import com.example.demo.model.Order;
import com.example.demo.model.Status;

import java.util.Objects;

public record OrderDTO(Long Id, String name, double price, Status status, CustomerDTO customer) {
    public OrderDTO {
        Objects.requireNonNull(Id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);
        Objects.requireNonNull(customer);
        Objects.requireNonNull(status);
    }

    public static OrderDTO fromOrder(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getName(),
                order.getPrice(),
                order.getStatus(),
                CustomerDTO.fromCustomer(order.getCustomer())
        );
    }
}