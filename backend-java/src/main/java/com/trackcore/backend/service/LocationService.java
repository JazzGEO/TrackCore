package com.trackcore.backend.service;

import com.trackcore.backend.entity.Location;
import com.trackcore.backend.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    public List<Location> findByBranch(String branch) {
        return locationRepository.findByBranch(branch);
    }

    public List<Location> findByBranchAndFloor(String branch, String floor) {
        return locationRepository.findByBranchAndFloor(branch, floor);
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }
}