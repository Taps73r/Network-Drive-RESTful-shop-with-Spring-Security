package com.example.demo.controller;

import com.example.demo.DTO.CombinedDTO;
import com.example.demo.service.NetworkDriveCategoryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class NetworkDriveCategoryController {

    private final NetworkDriveCategoryService networkDriveCategoryService;

    public NetworkDriveCategoryController(NetworkDriveCategoryService networkDriveCategoryService) {
        this.networkDriveCategoryService = networkDriveCategoryService;
    }
    @Cacheable(value = "category", key = "{#page, #size}")
    @GetMapping("/{categoryName}")
    public Page<CombinedDTO> getProductsByCategory(
            @PathVariable String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return networkDriveCategoryService.getProductsByCategory(categoryName, page, size);
    }
}
