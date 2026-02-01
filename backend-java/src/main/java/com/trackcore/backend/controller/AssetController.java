package com.trackcore.backend.controller;

import com.trackcore.backend.entity.Asset;
import com.trackcore.backend.entity.User;
import com.trackcore.backend.service.AssetService;
import com.trackcore.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AssetController {

    private final AssetService assetService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        return ResponseEntity.ok(assetService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long id) {
        return assetService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Asset>> getAssetsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(assetService.findByStatus(status));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Asset>> getAssetsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(assetService.findByCategory(category));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Asset>> getAvailableAssets() {
        return ResponseEntity.ok(assetService.findAvailableAssets());
    }

    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetService.save(asset));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestBody Asset asset) {
        return assetService.findById(id)
            .map(existing -> {
                asset.setId(id);
                return ResponseEntity.ok(assetService.save(asset));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<?> checkOutAsset(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            String notes = request.get("notes") != null ? request.get("notes").toString() : "";

            User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

            Asset asset = assetService.checkOut(id, user, notes);
            return ResponseEntity.ok(asset);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<?> checkInAsset(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> request) {
        try {
            String notes = request != null && request.get("notes") != null ? request.get("notes") : "";
            Asset asset = assetService.checkIn(id, notes);
            return ResponseEntity.ok(asset);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        assetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}