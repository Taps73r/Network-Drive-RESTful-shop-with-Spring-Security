package com.example.demo.DTO;

import com.example.demo.model.Order;

import java.util.Objects;

public record OrderDTO(Long orderId, String orderName, double orderPrice, CustomerDTO customer) {
    public OrderDTO {
        Objects.requireNonNull(orderId);
        Objects.requireNonNull(orderName);
        Objects.requireNonNull(orderPrice);
        Objects.requireNonNull(customer);
    }

    public static OrderDTO fromOrder(Order order) {
        return new OrderDTO(
                order.getOrderId(),
                order.getOrderName(),
                order.getOrderPrice(),
                CustomerDTO.fromCustomer(order.getCustomer())
        );
    }
}