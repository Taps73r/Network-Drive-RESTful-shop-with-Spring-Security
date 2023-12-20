package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    private Long Id;

    private String name;

    private double price;
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "order_network_drive",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "network_drive_id")
    )
    private List<NetworkDrive> networkDrives;

    public void addNetworkDrive(NetworkDrive networkDrive) {
        if (networkDrives == null) {
            networkDrives = new ArrayList<>();
        }
        networkDrives.add(networkDrive);
    }

    public void removeNetworkDrive(Long networkDriveId) {
        if (networkDrives != null) {
            networkDrives.removeIf(networkDrive -> networkDrive.getNetworkDriveId().equals(networkDriveId));
        }
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + Id +
                ", orderName='" + name + '\'' +
                ", orderPrice='" + price + '\'' +
                ", customer=" + customer +
                '}';
    }
}