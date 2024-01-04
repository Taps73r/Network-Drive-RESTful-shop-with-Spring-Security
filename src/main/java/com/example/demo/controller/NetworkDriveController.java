package com.example.demo.controller;

import com.example.demo.DTO.NetworkDriveDTO;
import com.example.demo.model.NetworkDrive;
import com.example.demo.service.NetworkDriveService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/network_drives")
public class NetworkDriveController {
    private final NetworkDriveService networkDriveService;

    public NetworkDriveController(NetworkDriveService networkDriveService) {
        this.networkDriveService = networkDriveService;
    }

    // Get Network drives with Pagination
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Cacheable(value = "networkDrives", key = "{#page, #size}")
    public Page<NetworkDriveDTO> getNas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (size > 20) {
            throw new IllegalArgumentException("Size cannot exceed 20");
        }
        else {
            return networkDriveService.getNas(page, size).map(NetworkDriveDTO::fromNetworkDrive);
        }
    }

    //Update Network drive by ID
    @PutMapping("/{nasId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateNas(
            @PathVariable Long nasId,
            @RequestBody NetworkDrive updatedNetworkDrive) {
        List<String> validationErrors = validateNetworkDrive(updatedNetworkDrive);
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrors);
        }
        else {
            NetworkDrive updatedNas = networkDriveService.updateNas(nasId, updatedNetworkDrive);
            return ResponseEntity.ok(NetworkDriveDTO.fromNetworkDrive(updatedNas));
        }
    }

    //Delete Network drive by ID
    @DeleteMapping("/{nasId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteNas(@PathVariable Long nasId) {
        networkDriveService.deleteNas(nasId);
        return ResponseEntity.ok("Nas deleted successfully");
    }

    //Post Network drive
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createNetworkDrive(@RequestBody NetworkDrive networkDrive) {
        List<String> validationErrors = validateNetworkDrive(networkDrive);
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrors);
        } else {
            NetworkDrive createdNetworkDrive = networkDriveService.addNas(networkDrive);
            return ResponseEntity.ok(NetworkDriveDTO.fromNetworkDrive(createdNetworkDrive));
        }
    }
    // Get Network drive by ID
    @GetMapping("/{nasId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Cacheable(value = "networkDriveById", key = "#nasId")
    public ResponseEntity<NetworkDriveDTO> getNasById(@PathVariable Long nasId) {
        NetworkDrive networkDrive = networkDriveService.getNetworkDriveById(nasId);
        if (networkDrive != null) {
            return ResponseEntity.ok(NetworkDriveDTO.fromNetworkDrive(networkDrive));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Search Network drive by name
    @GetMapping("/searchByName")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<NetworkDriveDTO>> searchNasByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (size > 20) {
            throw new IllegalArgumentException("Size cannot exceed 20");
        }
        else {
            Pageable pageable = PageRequest.of(page, size);
            List<NetworkDrive> networkDrives = networkDriveService.searchNasByName(name, pageable);
            return ResponseEntity.ok(NetworkDriveDTO.fromNetworkDriveList(networkDrives));
        }
    }

    // Search Network drive by price range
    @GetMapping("/searchByPrice")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<NetworkDriveDTO>> searchNasByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (size > 20) {
            throw new IllegalArgumentException("Size cannot exceed 20");
        }
        else {
            Pageable pageable = PageRequest.of(page, size);
            List<NetworkDrive> networkDrives = networkDriveService.searchNasByPriceRange(minPrice, maxPrice, pageable);
            return ResponseEntity.ok(NetworkDriveDTO.fromNetworkDriveList(networkDrives));
        }
    }

    private List<String> validateNetworkDrive(NetworkDrive networkDrive) {
        List<String> errors = new ArrayList<>();
        if (networkDrive.getNetworkDriveName() == null || networkDrive.getNetworkDriveName().length() < 3 || networkDrive.getNetworkDriveName().length() > 25) {
            errors.add("Name must be between 3 and 25 characters");
        }
        if (networkDrive.getPrice() <= 0 || networkDrive.getPrice() > 2000000) {
            errors.add("Price must be greater than 0 and not exceed 2000000");
        }
        if (networkDrive.getNetworkDriveInfo() != null && networkDrive.getNetworkDriveInfo().length() > 100) {
            errors.add("Info cannot exceed 100 characters");
        }
        return errors;
    }
}
