package com.example.demo.DTO;

import com.example.demo.model.NetworkDriveCategory;

import java.util.Objects;

public record NetworkDriveCategoryDTO(Long id, String categoryName) {
    public NetworkDriveCategoryDTO {
        Objects.requireNonNull(id);
        Objects.requireNonNull(categoryName);
    }

    public static NetworkDriveCategoryDTO fromNetworkDriveCategory(NetworkDriveCategory networkDriveCategory) {
        return new NetworkDriveCategoryDTO(
                networkDriveCategory.getId(),
                networkDriveCategory.getCategoryName()
        );
    }
}
