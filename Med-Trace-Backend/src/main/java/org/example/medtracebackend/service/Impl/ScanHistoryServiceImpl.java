package org.example.medtracebackend.service.Impl;

import org.example.medtracebackend.dto.ScanHistoryDTO;
import org.example.medtracebackend.entity.ScanHistory;
import org.example.medtracebackend.repository.BatchRepo;
import org.example.medtracebackend.repository.ScanHistoryRepo;
import org.example.medtracebackend.repository.UserRepo;
import org.example.medtracebackend.service.ScanHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScanHistoryServiceImpl implements ScanHistoryService {

    @Autowired
    private ScanHistoryRepo scanHistoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BatchRepo batchRepo;

    @Override
    public void saveScanRecord(Long userId, Long batchId, String status) {
        ScanHistory history = new ScanHistory();
        history.setUser(userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        history.setBatch(batchRepo.findById(batchId).orElseThrow(() -> new RuntimeException("Batch not found")));
        history.setScanDate(LocalDateTime.now());
        history.setStatus(status);
        scanHistoryRepo.save(history);
    }

    @Override
    public List<ScanHistoryDTO> getHistoryByUserId(Long userId) {
        return scanHistoryRepo.findByUserIdOrderByScanDateDesc(userId).stream()
                .map(history -> {
                    ScanHistoryDTO dto = new ScanHistoryDTO();
                    dto.setId(history.getId());
                    dto.setScanDate(history.getScanDate());
                    dto.setStatus(history.getStatus());
                    dto.setBatchNo(history.getBatch().getBatchNumber());
                    dto.setMedicineName(history.getBatch().getMedicine().getName());
                    dto.setManufacturer(history.getBatch().getMedicine().getManufacturer());
                    return dto;
                }).collect(Collectors.toList());
    }
    @Override
    public List<ScanHistoryDTO> getAllHistory() {
        return scanHistoryRepo.findAll().stream()
                .map(history -> {
                    ScanHistoryDTO dto = new ScanHistoryDTO();
                    dto.setId(history.getId());
                    dto.setScanDate(history.getScanDate());
                    dto.setStatus(history.getStatus());
                    dto.setBatchNo(history.getBatch().getBatchNumber());
                    dto.setMedicineName(history.getBatch().getMedicine().getName());
                    dto.setManufacturer(history.getBatch().getMedicine().getManufacturer());
                    dto.setCustomerName(history.getUser().getFullName());
                    return dto;
                }).collect(Collectors.toList());
    }
}