package com.trackcore.backend.service;

import com.trackcore.backend.entity.Asset;
import com.trackcore.backend.entity.CustodyHistory;
import com.trackcore.backend.entity.User;
import com.trackcore.backend.repository.AssetRepository;
import com.trackcore.backend.repository.CustodyHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final CustodyHistoryRepository custodyHistoryRepository;

    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    public Optional<Asset> findById(Long id) {
        return assetRepository.findById(id);
    }

    public List<Asset> findByStatus(String status) {
        return assetRepository.findByStatus(status);
    }

    public List<Asset> findByCategory(String category) {
        return assetRepository.findByCategory(category);
    }

    public List<Asset> findAvailableAssets() {
        return assetRepository.findAvailableAssets();
    }

    public Asset save(Asset asset) {
        return assetRepository.save(asset);
    }

    @Transactional
    public Asset checkOut(Long assetId, User user, String notes) {
        Asset asset = assetRepository.findById(assetId)
            .orElseThrow(() -> new RuntimeException("Asset not found"));

        Optional<CustodyHistory> activeCustody = custodyHistoryRepository.findActiveCustodyByAssetId(assetId);
        if (activeCustody.isPresent()) {
            throw new RuntimeException("Asset is already assigned to another user");
        }

        asset.setStatus("em_uso");
        assetRepository.save(asset);

        CustodyHistory custody = new CustodyHistory();
        custody.setAsset(asset);
        custody.setUser(user);
        custody.setStartDate(LocalDateTime.now());
        custody.setNotes(notes);
        custodyHistoryRepository.save(custody);

        return asset;
    }

    @Transactional
    public Asset checkIn(Long assetId, String notes) {
        Asset asset = assetRepository.findById(assetId)
            .orElseThrow(() -> new RuntimeException("Asset not found"));

        CustodyHistory activeCustody = custodyHistoryRepository.findActiveCustodyByAssetId(assetId)
            .orElseThrow(() -> new RuntimeException("No active custody found for this asset"));

        activeCustody.setEndDate(LocalDateTime.now());
        if (notes != null && !notes.isEmpty()) {
            activeCustody.setNotes(activeCustody.getNotes() + " | Devolução: " + notes);
        }
        custodyHistoryRepository.save(activeCustody);

        asset.setStatus("disponivel");
        assetRepository.save(asset);

        return asset;
    }

    public void deleteById(Long id) {
        assetRepository.deleteById(id);
    }
}