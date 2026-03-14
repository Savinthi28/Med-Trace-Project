package org.example.medtracebackend.service.Impl;

import org.example.medtracebackend.dto.PharmacyDTO;
import org.example.medtracebackend.entity.Pharmacy;
import org.example.medtracebackend.exception.ResourceNotFoundException;
import org.example.medtracebackend.repository.PharmacyRepo;
import org.example.medtracebackend.service.PharmacyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacyServiceImpl implements PharmacyService {
    @Autowired
    private PharmacyRepo pharmacyRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PharmacyDTO savePharmacy(PharmacyDTO pharmacyDTO){
        if(pharmacyDTO == null)
            throw new NullPointerException("pharmacy data is null");
        Pharmacy pharmacy = modelMapper.map(pharmacyDTO, Pharmacy.class);
        return modelMapper.map(pharmacyRepo.save(pharmacy), PharmacyDTO.class);
    }

    @Override
    public List<PharmacyDTO> getAllPharmacies() {
        return modelMapper.map(pharmacyRepo.findAll(), new TypeToken<List<PharmacyDTO>>(){}.getType());
    }

    @Override
    public PharmacyDTO getPharmacyById(Long id) {
        Pharmacy pharmacy = pharmacyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found ID: " + id));
        return modelMapper.map(pharmacy, PharmacyDTO.class);
    }

    @Override
    public PharmacyDTO updatePharmacy(Long id, PharmacyDTO dto) {
        Pharmacy pharmacy = pharmacyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found to update "));
        pharmacy.setPharmacyName(dto.getPharmacyName());
        pharmacy.setLocation(dto.getLocation());
        pharmacy.setContactNumber(dto.getContactNumber());
        return modelMapper.map(pharmacyRepo.save(pharmacy), PharmacyDTO.class);
    }

    @Override
    public void deletePharmacy(Long id) {
        if (!pharmacyRepo.existsById(id))
            throw new ResourceNotFoundException("Pharmacy not found to delete ");
        pharmacyRepo.deleteById(id);
    }

    @Override
    public List<PharmacyDTO> getPharmaciesByLocation(String location) {
        return modelMapper.map(pharmacyRepo.findByLocationContainingIgnoreCase(location),
                new TypeToken<List<PharmacyDTO>>() {}.getType());
    }
}
