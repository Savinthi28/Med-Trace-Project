package org.example.medtracebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicineDTO {
    private Long id;
    private String name;
    private String manufacturer;
    private String category;

}