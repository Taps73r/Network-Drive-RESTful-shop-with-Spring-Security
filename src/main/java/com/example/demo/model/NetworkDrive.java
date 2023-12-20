package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "network_drive")
public class NetworkDrive {
    @Id
    @SequenceGenerator(
            name = "network_drive_sequence",
            sequenceName = "network_drive_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "network_drive_sequence"
    )
    private Long networkDrive_Id;

    private String networkDriveName;
    private double price;
    private String networkDriveInfo;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "order_network_drive",
            joinColumns = @JoinColumn(name = "network_drive_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private NetworkDriveCategory networkDriveCategory;

    public Long getNetworkDriveId() {
        return networkDrive_Id;
    }

    @Override
    public String toString() {
        return "networkDrive{" +
                "networkDriveId=" + networkDrive_Id +
                ", productName='" + networkDriveName + '\'' +
                ", price=" + price +
                ", order=" + orders +
                ", networkDriveCategory=" + networkDriveCategory +
                '}';
    }
}