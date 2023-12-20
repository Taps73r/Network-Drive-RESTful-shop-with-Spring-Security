package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.NetworkDrive;
import com.example.demo.model.Order;
import com.example.demo.model.Status;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public Page<Order> getAllOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size));
    }
    @Transactional
    public Order addOrderForCustomer(Long customerId, Order order, Status status) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));

        order.setCustomer(customer);
        order.setStatus(status);
        orderRepository.save(order);
        return order;
    }
    public Page<Order> getOrdersByStatus(Status status, int page, int size) {
        return orderRepository.findByStatus(status, PageRequest.of(page, size));
    }
    @Transactional
    public void deleteOrder(Long orderId) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        orderRepository.delete(existingOrder);
    }
    public Order getOrderById(Long orderId) {
        return getOrderByIdInternal(orderId);
    }
    private Order getOrderByIdInternal(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));
    }
    public void addOrder(Order order, Status status) {
        order.setStatus(status);
        orderRepository.save(order);
    }
    @Transactional
    public void removeNetworkDriveFromOrder(Long orderId, Long networkDriveId) {
        Order order = getOrderById(orderId);

        if (order != null) {
            order.removeNetworkDrive(networkDriveId);
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order not found with id: " + orderId);
        }
    }
    @Transactional
    public Order updateOrderStatus(Long orderId, Status newStatus) {
        Order order = getOrderByIdInternal(orderId);

        if (order != null) {
            order.setStatus(newStatus);
            orderRepository.save(order);
            return order;
        } else {
            throw new IllegalArgumentException("Order not found with id: " + orderId);
        }
    }
    public Page<NetworkDrive> getNetworkDrivesForOrder(Long orderId, Pageable pageable) {
        Order order = getOrderByIdInternal(orderId);

        if (order != null) {
            List<NetworkDrive> networkDrives = order.getNetworkDrives();
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), networkDrives.size());
            return new PageImpl<>(networkDrives.subList(start, end), pageable, networkDrives.size());
        } else {
            throw new IllegalArgumentException("Order not found with id: " + orderId);
        }
    }

}
