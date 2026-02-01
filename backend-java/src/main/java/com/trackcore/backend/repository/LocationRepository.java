package com.trackcore.backend.repository;

import com.trackcore.backend.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    
    List<Location> findByBranch(String branch);
    
    List<Location> findByBranchAndFloor(String branch, String floor);
}