package com.example.demo.DTO;

import com.example.demo.model.NetworkDrive;

import java.util.Objects;

public record CombinedDTO(
        Long networkDriveId,
        String networkDriveName,
        double price,
        String networkDriveInfo,
        NetworkDriveCategoryDTO networkDriveCategory
) {
    public CombinedDTO {
        Objects.requireNonNull(networkDriveId);
        Objects.requireNonNull(networkDriveName);
        Objects.requireNonNull(price);
        Objects.requireNonNull(networkDriveInfo);
        Objects.requireNonNull(networkDriveCategory);
    }

    public static CombinedDTO fromNetworkDrive(NetworkDrive networkDrive) {
        return new CombinedDTO(
                networkDrive.getNetworkDrive_Id(),
                networkDrive.getNetworkDriveName(),
                networkDrive.getPrice(),
                networkDrive.getNetworkDriveInfo(),
                NetworkDriveCategoryDTO.fromNetworkDriveCategory(networkDrive.getNetworkDriveCategory())
        );
    }
}