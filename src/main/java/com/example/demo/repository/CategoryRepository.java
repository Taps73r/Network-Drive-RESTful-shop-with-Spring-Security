package com.example.demo.repository;

import com.example.demo.model.NetworkDriveCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<NetworkDriveCategory, Long> {
    NetworkDriveCategory findByCategoryName(String categoryName);
}
