package com.trackcore.backend.controller;

import com.trackcore.backend.entity.Location;
import com.trackcore.backend.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        return locationService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/branch/{branch}")
    public ResponseEntity<List<Location>> getLocationsByBranch(@PathVariable String branch) {
        return ResponseEntity.ok(locationService.findByBranch(branch));
    }

    @GetMapping("/branch/{branch}/floor/{floor}")
    public ResponseEntity<List<Location>> getLocationsByBranchAndFloor(
            @PathVariable String branch,
            @PathVariable String floor) {
        return ResponseEntity.ok(locationService.findByBranchAndFloor(branch, floor));
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.save(location));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location location) {
        return locationService.findById(id)
            .map(existing -> {
                location.setId(id);
                return ResponseEntity.ok(locationService.save(location));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}