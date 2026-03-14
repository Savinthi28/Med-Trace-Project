package org.example.medtracebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PharmacyDTO {
    private Long id;
    private String pharmacyName;
    private String licenseNumber;
    private String location;
    private String contactNumber;
}
