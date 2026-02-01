package com.trackcore.backend.service;

import com.trackcore.backend.entity.CustodyHistory;
import com.trackcore.backend.repository.CustodyHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustodyHistoryService {

    private final CustodyHistoryRepository custodyHistoryRepository;

    public List<CustodyHistory> findAll() {
        return custodyHistoryRepository.findAll();
    }

    public Optional<CustodyHistory> findById(Long id) {
        return custodyHistoryRepository.findById(id);
    }

    public List<CustodyHistory> findByAssetId(Long assetId) {
        return custodyHistoryRepository.findByAssetId(assetId);
    }

    public List<CustodyHistory> findByUserId(Long userId) {
        return custodyHistoryRepository.findByUserId(userId);
    }

    public Optional<CustodyHistory> findActiveCustodyByAssetId(Long assetId) {
        return custodyHistoryRepository.findActiveCustodyByAssetId(assetId);
    }

    public List<CustodyHistory> findActiveCustodiesByUserId(Long userId) {
        return custodyHistoryRepository.findActiveCustodiesByUserId(userId);
    }

    public CustodyHistory save(CustodyHistory custodyHistory) {
        return custodyHistoryRepository.save(custodyHistory);
    }
}