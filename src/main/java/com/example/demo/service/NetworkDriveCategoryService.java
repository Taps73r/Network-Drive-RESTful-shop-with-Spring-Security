package com.example.demo.service;

import com.example.demo.DTO.CombinedDTO;
import com.example.demo.model.NetworkDrive;
import com.example.demo.model.NetworkDriveCategory;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.NetworkDriveRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class NetworkDriveCategoryService {

    private final CategoryRepository categoryRepository;
    private final NetworkDriveRepository networkDriveRepository;

    public NetworkDriveCategoryService(CategoryRepository categoryRepository, NetworkDriveRepository networkDriveRepository) {
        this.categoryRepository = categoryRepository;
        this.networkDriveRepository = networkDriveRepository;
    }

    public Page<CombinedDTO> getProductsByCategory(String categoryName, int page, int size) {
        NetworkDriveCategory category = categoryRepository.findByCategoryName(categoryName);
        if (category == null) {
            throw new IllegalArgumentException("Категорію не знайдено: " + categoryName);
        }

        Page<NetworkDrive> networkDrivePage = networkDriveRepository.findByNetworkDriveCategory(category, PageRequest.of(page, size));

        return networkDrivePage.map(NetworkDriveCategoryService::mapToCombinedDTO);
    }

    private static CombinedDTO mapToCombinedDTO(NetworkDrive networkDrive) {
        return CombinedDTO.fromNetworkDrive(networkDrive);
    }
}