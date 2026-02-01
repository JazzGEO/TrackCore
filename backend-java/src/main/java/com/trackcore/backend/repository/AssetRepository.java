package com.trackcore.backend.repository;

import com.trackcore.backend.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    List<Asset> findByStatus(String status);
    
    List<Asset> findByCategory(String category);
    
    List<Asset> findByLocationId(Long locationId);
    
    @Query("SELECT a FROM Asset a WHERE a.status = 'disponivel'")
    List<Asset> findAvailableAssets();
    
    @Query("SELECT a FROM Asset a WHERE a.status = 'em_uso'")
    List<Asset> findAssetsInUse();
}