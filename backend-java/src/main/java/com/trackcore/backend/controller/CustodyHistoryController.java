package com.trackcore.backend.controller;

import com.trackcore.backend.entity.CustodyHistory;
import com.trackcore.backend.service.CustodyHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/custody-history")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustodyHistoryController {

    private final CustodyHistoryService custodyHistoryService;

    @GetMapping
    public ResponseEntity<List<CustodyHistory>> getAllCustodyHistory() {
        return ResponseEntity.ok(custodyHistoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustodyHistory> getCustodyHistoryById(@PathVariable Long id) {
        return custodyHistoryService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<CustodyHistory>> getCustodyHistoryByAssetId(@PathVariable Long assetId) {
        return ResponseEntity.ok(custodyHistoryService.findByAssetId(assetId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CustodyHistory>> getCustodyHistoryByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(custodyHistoryService.findByUserId(userId));
    }

    @GetMapping("/asset/{assetId}/active")
    public ResponseEntity<CustodyHistory> getActiveCustodyByAssetId(@PathVariable Long assetId) {
        return custodyHistoryService.findActiveCustodyByAssetId(assetId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<CustodyHistory>> getActiveCustodiesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(custodyHistoryService.findActiveCustodiesByUserId(userId));
    }
}