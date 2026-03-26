package org.example.medtracebackend.repository;

import org.example.medtracebackend.entity.ScanHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
    public interface ScanHistoryRepo extends JpaRepository<ScanHistory, Long> {
        List<ScanHistory> findByUserIdOrderByScanDateDesc(Long userId);
    }

