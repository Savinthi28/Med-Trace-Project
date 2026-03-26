package org.example.medtracebackend.service;

import org.example.medtracebackend.dto.ScanHistoryDTO;
import java.util.List;

public interface ScanHistoryService {
    void saveScanRecord(Long userId, Long batchId, String status);
    List<ScanHistoryDTO> getHistoryByUserId(Long userId);
    List<ScanHistoryDTO> getAllHistory();
}