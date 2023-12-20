package com.example.demo.DTO;

import com.example.demo.model.NetworkDrive;
import com.example.demo.model.NetworkDriveCategory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record NetworkDriveDTO(Long networkDriveId,
                              String networkDriveName,
                              double price,
                              String networkDriveInfo,
                              NetworkDriveCategory networkDriveCategory) {
    public NetworkDriveDTO {
        Objects.requireNonNull(networkDriveId);
        Objects.requireNonNull(networkDriveName);
        Objects.requireNonNull(price);
        Objects.requireNonNull(networkDriveInfo);
        Objects.requireNonNull(networkDriveCategory);
    }

    public static NetworkDriveDTO fromNetworkDrive(NetworkDrive networkDrive) {
        return new NetworkDriveDTO(
                networkDrive.getNetworkDrive_Id(),
                networkDrive.getNetworkDriveName(),
                networkDrive.getPrice(),
                networkDrive.getNetworkDriveInfo(),
                networkDrive.getNetworkDriveCategory()
        );
    }

    public static List<NetworkDriveDTO> fromNetworkDriveList(List<NetworkDrive> networkDrives) {
        return networkDrives.stream()
                .map(NetworkDriveDTO::fromNetworkDrive)
                .collect(Collectors.toList());
    }

}
