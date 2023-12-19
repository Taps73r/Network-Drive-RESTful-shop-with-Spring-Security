package com.example.demo.controller;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Get Orders with Pagination
    @GetMapping
    public Page<OrderDTO> getAllCustomerOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.getAllOrders(page, size).map(OrderDTO::fromOrder);
    }
    //getStatus
    //Create Order
    @PostMapping("/{customerId}")
    public ResponseEntity<?> createOrderForCustomer(
            @PathVariable Long customerId,
            @RequestBody Order order) {

        List<String> validationErrors = validateOrder(order);
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrors);
        }
        else {
            Order createdOrder = orderService.addOrderForCustomer(customerId, order);
            OrderDTO responseDTO = OrderDTO.fromOrder(createdOrder);
            return ResponseEntity.ok(responseDTO);
        }
    }

    private List<String> validateOrder(Order order) {
        List<String> errors = new ArrayList<>();
        if (order.getOrderName() == null || order.getOrderName().length() < 3 || order.getOrderName().length() > 20) {
            errors.add("Order name must be between 3 and 20 characters");
        }
        if (order.getOrderPrice() <= 0 || order.getOrderPrice() > 2000000) {
            errors.add("Order price must be greater than 0 and not exceed 2000000");
        }
        return errors;
    }

    //Delete Order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully");
    }

}
