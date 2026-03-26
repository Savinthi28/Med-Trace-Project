package org.example.medtracebackend.controller;

import org.example.medtracebackend.dto.ScanHistoryDTO;
import org.example.medtracebackend.service.ScanHistoryService;
import org.example.medtracebackend.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scans")
@CrossOrigin(origins = "*")
public class ScanHistoryController {

    @Autowired
    private ScanHistoryService scanHistoryService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<ScanHistoryDTO>>> getUserHistory(@PathVariable Long userId) {
        List<ScanHistoryDTO> history = scanHistoryService.getHistoryByUserId(userId);
        return new ResponseEntity<>(new APIResponse<>(200, "History fetched", history), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<ScanHistoryDTO>>> getAllScans() {
        List<ScanHistoryDTO> history = scanHistoryService.getAllHistory();
        return new ResponseEntity<>(new APIResponse<>(200, "All scans fetched", history), HttpStatus.OK);
    }
}