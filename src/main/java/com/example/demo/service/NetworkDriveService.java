package com.example.demo.service;

import com.example.demo.model.NetworkDrive;
import com.example.demo.repository.NetworkDriveRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NetworkDriveService {
    private final NetworkDriveRepository networkDriveRepository;

    public NetworkDriveService(NetworkDriveRepository networkDriveRepository) {
        this.networkDriveRepository = networkDriveRepository;
    }
    @Transactional
    public NetworkDrive getNetworkDriveById(Long networkDriveId) {
        return networkDriveRepository.findById(networkDriveId)
                .orElseThrow(() -> new IllegalArgumentException("NetworkDrive not found with id: " + networkDriveId));
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
    public NetworkDrive addNas(NetworkDrive networkDrive) {
        networkDriveRepository.save(networkDrive);
        return networkDrive;
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
    public List<NetworkDrive> searchNasByName(String name, Pageable pageable) {
        Page<NetworkDrive> pageResult = networkDriveRepository.findByNetworkDriveNameContainingIgnoreCase(name, pageable);
        return pageResult.getContent();
    }

    public List<NetworkDrive> searchNasByPriceRange(double minPrice, double maxPrice, Pageable pageable) {
        Page<NetworkDrive> pageResult = networkDriveRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        return pageResult.getContent();
    }
}
