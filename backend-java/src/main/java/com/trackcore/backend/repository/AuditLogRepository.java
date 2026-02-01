package com.trackcore.backend.repository;

import com.trackcore.backend.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    List<AuditLog> findByTableName(String tableName);
    
    List<AuditLog> findByTableNameAndRecordId(String tableName, Long recordId);
    
    List<AuditLog> findByChangedBy(String changedBy);
    
    List<AuditLog> findByChangedAtBetween(LocalDateTime start, LocalDateTime end);
}