package org.example.medtracebackend.service;

import org.example.medtracebackend.dto.PharmacyDTO;

import java.util.List;

public interface PharmacyService {
    PharmacyDTO savePharmacy(PharmacyDTO pharmacyDTO);
    List<PharmacyDTO> getAllPharmacies();
    PharmacyDTO getPharmacyById(Long id);

    PharmacyDTO updatePharmacy(Long id, PharmacyDTO dto);

    void deletePharmacy(Long id);
    List<PharmacyDTO> getPharmaciesByLocation(String location);

    Long getPharmacyCount();
}
