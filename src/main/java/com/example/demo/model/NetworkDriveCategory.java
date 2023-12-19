package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "network_drive_category")
public class NetworkDriveCategory {
    @Id
    @SequenceGenerator(
            name = "network_drive_category_sequence",
            sequenceName = "network_drive_category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "network_drive_category_sequence"
    )
    private Long id;

    private String categoryName;

    @Override
    public String toString() {
        return "NetworkDriveCategory{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
