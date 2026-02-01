package com.trackcore.backend.repository;

import com.trackcore.backend.entity.CustodyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustodyHistoryRepository extends JpaRepository<CustodyHistory, Long> {
    
    List<CustodyHistory> findByAssetId(Long assetId);
    
    List<CustodyHistory> findByUserId(Long userId);
    
    @Query("SELECT ch FROM CustodyHistory ch WHERE ch.asset.id = :assetId AND ch.endDate IS NULL")
    Optional<CustodyHistory> findActiveCustodyByAssetId(Long assetId);
    
    @Query("SELECT ch FROM CustodyHistory ch WHERE ch.user.id = :userId AND ch.endDate IS NULL")
    List<CustodyHistory> findActiveCustodiesByUserId(Long userId);
}