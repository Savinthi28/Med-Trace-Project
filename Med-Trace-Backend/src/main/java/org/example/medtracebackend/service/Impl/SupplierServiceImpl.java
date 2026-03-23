package org.example.medtracebackend.service.Impl;

import org.example.medtracebackend.dto.SupplierDTO;
import org.example.medtracebackend.entity.Supplier;
import org.example.medtracebackend.exception.ResourceNotFoundException;
import org.example.medtracebackend.repository.SupplierRepo;
import org.example.medtracebackend.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) {
        if (supplierDTO == null) {
            throw new NullPointerException("Supplier details cannot be null");
        }
        Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);
        return modelMapper.map(supplierRepo.save(supplier), SupplierDTO.class);
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return modelMapper.map(supplierRepo.findAll(), new TypeToken<List<SupplierDTO>>() {}.getType());
    }

    @Override
    public SupplierDTO getSupplierById(Long id) {
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Supplier not found with id: " + id));
        return modelMapper.map(supplier, SupplierDTO.class);
    }

    @Override
    public SupplierDTO updateSupplier(Long id, SupplierDTO dto) {
        if (dto == null) {
            throw new NullPointerException("Update details cannot be null");
        }
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Supplier not found update: " + id));
        supplier.setName(dto.getName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplier.setEmail(dto.getEmail());
        supplier.setCategory(dto.getCategory());

        return modelMapper.map(supplierRepo.save(supplier), SupplierDTO.class);
    }

    @Override
    public void deleteSupplier(Long id) {
        if (id == null) {
            throw new NullPointerException("Supplier id cannot be null");
        }
        if (!supplierRepo.existsById(id)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + id);
        }
        supplierRepo.deleteById(id);
    }

    @Override
    public List<SupplierDTO> searchSuppliers(String name) {
        if (name == null) {
            throw new NullPointerException("Search query cannot be null");
        }
        return modelMapper.map(supplierRepo.findByNameContainingIgnoreCase(name), new TypeToken<List<SupplierDTO>>() {}.getType());
    }

    @Override
    public Long getSupplierCount() {
        return supplierRepo.count();
    }
}
