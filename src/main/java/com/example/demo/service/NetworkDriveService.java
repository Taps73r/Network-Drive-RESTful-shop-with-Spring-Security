package com.example.demo.service;

import com.example.demo.model.NetworkDrive;
import com.example.demo.model.Order;
import com.example.demo.repository.NetworkDriveRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NetworkDriveService {
    private final NetworkDriveRepository networkDriveRepository;
    private final OrderRepository orderRepository;

    public NetworkDriveService(NetworkDriveRepository networkDriveRepository, OrderRepository orderRepository) {
        this.networkDriveRepository = networkDriveRepository;
        this.orderRepository = orderRepository;
    }

    public Page<NetworkDrive> getNas(int page, int size) {
        return networkDriveRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public void deleteNas(Long networkDriveId) {
        NetworkDrive existingNetworkDrive = networkDriveRepository.findById(networkDriveId)
                .orElseThrow(() -> new IllegalArgumentException("Nas not found with id: " + networkDriveId));

        networkDriveRepository.delete(existingNetworkDrive);
    }

    @Transactional
    public void addNasForOrder(Long orderId, NetworkDrive networkDrive) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        networkDrive.setOrders(order);
        networkDriveRepository.save(networkDrive);
    }

    @Transactional
    public NetworkDrive updateNas(Long networkDriveId, NetworkDrive updatedNetworkDrive) {
        NetworkDrive existingNetworkDrive = networkDriveRepository.findById(networkDriveId)
                .orElseThrow(() -> new IllegalArgumentException("Nas not found with id: " + networkDriveId));

        existingNetworkDrive.setNetworkDriveName(updatedNetworkDrive.getNetworkDriveName());
        existingNetworkDrive.setPrice(updatedNetworkDrive.getPrice());
        existingNetworkDrive.setNetworkDriveInfo(updatedNetworkDrive.getNetworkDriveInfo());
        existingNetworkDrive.setNetworkDriveCategory(updatedNetworkDrive.getNetworkDriveCategory());

        return networkDriveRepository.save(existingNetworkDrive);
    }
}
