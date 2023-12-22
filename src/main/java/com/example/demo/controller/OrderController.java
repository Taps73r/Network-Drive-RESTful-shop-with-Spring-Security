package com.example.demo.controller;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.Status;
import com.example.demo.service.OrderService;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "orders", key = "{#page, #size}")
    public Page<OrderDTO> getAllCustomerOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.getAllOrders(page, size).map(OrderDTO::fromOrder);
    }

    // Get Orders by Status with Pagination
    @GetMapping("/{status}")
    @Cacheable(value = "orderbyStatus", key = "{#page, #size}")
    public Page<OrderDTO> getOrdersByStatus(
            @PathVariable Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.getOrdersByStatus(status, page, size).map(OrderDTO::fromOrder);
    }

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
            Order createdOrder = orderService.addOrderForCustomer(customerId, order, Status.NEW);
            OrderDTO responseDTO = OrderDTO.fromOrder(createdOrder);
            return ResponseEntity.ok(responseDTO);
        }
    }
    // Confirm Order
    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<OrderDTO> confirmOrder(@PathVariable Long orderId) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, Status.CONFIRMED);
        OrderDTO responseDTO = OrderDTO.fromOrder(updatedOrder);
        return ResponseEntity.ok(responseDTO);
    }

    // Cancel Order
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable Long orderId) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, Status.CANCELED);
        OrderDTO responseDTO = OrderDTO.fromOrder(updatedOrder);
        return ResponseEntity.ok(responseDTO);
    }

    // Mark Order as Done
    @PostMapping("/{orderId}/done")
    public ResponseEntity<OrderDTO> markOrderAsDone(@PathVariable Long orderId) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, Status.DONE);
        OrderDTO responseDTO = OrderDTO.fromOrder(updatedOrder);
        return ResponseEntity.ok(responseDTO);
    }
    private List<String> validateOrder(Order order) {
        List<String> errors = new ArrayList<>();
        if (order.getName() == null || order.getName().length() < 3 || order.getName().length() > 20) {
            errors.add("Order name must be between 3 and 20 characters");
        }
        if (order.getPrice() <= 0 || order.getPrice() > 2000000) {
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
