package org.example.medtracebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanHistoryDTO {
    private Long id;
    private String medicineName;
    private String batchNo;
    private String manufacturer;
    private LocalDateTime scanDate;
    private String status;
    private String customerName;
}