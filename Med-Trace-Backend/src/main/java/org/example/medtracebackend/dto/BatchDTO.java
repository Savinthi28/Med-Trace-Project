package org.example.medtracebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchDTO {
    private Long id;
    private String batchNumber;
    private String qrCodeData;
    private LocalDate expiryDate;
    private int stockQuantity;
    private String status;
    private Long medicineId;
}
