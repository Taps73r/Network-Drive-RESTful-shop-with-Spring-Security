package com.example.demo.controller;

import com.example.demo.model.NetworkDrive;
import com.example.demo.model.Order;
import com.example.demo.model.Status;
import com.example.demo.service.NetworkDriveService;
import com.example.demo.service.OrderService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order-network-drives")
public class OrderNetworkDriveController {

    private final OrderService orderService;
    private final NetworkDriveService networkDriveService;

    public OrderNetworkDriveController(OrderService orderService, NetworkDriveService networkDriveService) {
        this.orderService = orderService;
        this.networkDriveService = networkDriveService;
    }

    // Add NetworkDrive to Order
    @PostMapping("/{orderId}/{networkDriveId}")
    public ResponseEntity<?> addNetworkDriveToOrder(
            @PathVariable Long orderId,
            @PathVariable Long networkDriveId) {

        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            return ResponseEntity.badRequest().body("Order not found");
        }

        if (!Status.NEW.equals(order.getStatus())) {
            return ResponseEntity.badRequest().body("Cannot add NetworkDrive to the order. The order is no longer in NEW status.");
        }

        NetworkDrive networkDrive = networkDriveService.getNetworkDriveById(networkDriveId);

        if (networkDrive == null) {
            return ResponseEntity.badRequest().body("NetworkDrive not found");
        }

        order.addNetworkDrive(networkDrive);
        orderService.addOrder(order, Status.NEW);

        return ResponseEntity.ok("NetworkDrive added to Order successfully");
    }

    // Delete NetworkDrive from Order
    @DeleteMapping("/{orderId}/{networkDriveId}")
    public ResponseEntity<String> removeNetworkDriveFromOrder(
            @PathVariable Long orderId,
            @PathVariable Long networkDriveId) {

        try {
            orderService.removeNetworkDriveFromOrder(orderId, networkDriveId);
            return ResponseEntity.ok("NetworkDrive removed from Order successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("NetworkDrive not found in Order");
        }
    }

    // Get all NetworkDrive from Order with Pagination
    @GetMapping("/{orderId}")
    @Cacheable(value = "NetworkdrivesfromOrder", key = "{#page, #size}")
    public ResponseEntity<Page<NetworkDrive>> getNetworkDrivesForOrder(
            @PathVariable Long orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Page<NetworkDrive> networkDrivesPage = orderService.getNetworkDrivesForOrder(orderId, PageRequest.of(page, size));
        return ResponseEntity.ok(networkDrivesPage);
    }

}
