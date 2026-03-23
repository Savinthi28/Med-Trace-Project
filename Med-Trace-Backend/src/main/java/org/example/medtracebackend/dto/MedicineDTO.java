package org.example.medtracebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicineDTO {
    private Long id;
    private String name;
    private String manufacturer;
    private String category;
    private List<BatchDTO> batches;
    private BatchDTO initialBatch;
}