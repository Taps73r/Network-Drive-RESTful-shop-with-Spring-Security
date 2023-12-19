package com.example.demo.DTO;

import com.example.demo.model.NetworkDrive;
import com.example.demo.model.NetworkDriveCategory;

import java.util.Objects;

public record NetworkDriveDTO(Long networkDriveId,
                              String networkDriveName,
                              double price,
                              String networkDriveInfo,
                              NetworkDriveCategory networkDriveCategory,
                              OrderDTO order) {
    public NetworkDriveDTO {
        Objects.requireNonNull(networkDriveId);
        Objects.requireNonNull(networkDriveName);
        Objects.requireNonNull(price);
        Objects.requireNonNull(networkDriveInfo);
        Objects.requireNonNull(networkDriveCategory);
        Objects.requireNonNull(order);
    }

    public static NetworkDriveDTO fromNetworkDrive(NetworkDrive networkDrive) {
        return new NetworkDriveDTO(
                networkDrive.getNetworkDrive_Id(),
                networkDrive.getNetworkDriveName(),
                networkDrive.getPrice(),
                networkDrive.getNetworkDriveInfo(),
                networkDrive.getNetworkDriveCategory(),
                OrderDTO.fromOrder(networkDrive.getOrders())
        );
    }
}
