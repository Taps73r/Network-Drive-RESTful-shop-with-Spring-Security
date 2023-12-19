package com.example.demo.repository;

import com.example.demo.model.NetworkDrive;
import com.example.demo.model.NetworkDriveCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkDriveRepository extends JpaRepository<NetworkDrive, Long> {
    Page<NetworkDrive> findByNetworkDriveCategory(NetworkDriveCategory category, Pageable pageable);
}