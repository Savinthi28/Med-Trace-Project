package org.example.medtracebackend.service.Impl;

import org.example.medtracebackend.entity.Batch;
import org.example.medtracebackend.entity.Medicine;
import org.example.medtracebackend.repository.BatchRepo;
import org.example.medtracebackend.repository.MedicineRepo;
import org.example.medtracebackend.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {
    @Autowired
    private MedicineRepo medicineRepo;

    @Autowired
    private BatchRepo batchRepo;

    @Override
    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepo.save(medicine);
    }

    @Override
    public Batch addBatchToMedicine(Long medicineId, Batch batch) {
        Medicine medicine = medicineRepo.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
        batch.setMedicine(medicine);
        return batchRepo.save(batch);
    }

    @Override
    public List<Medicine> getAllMedicine() {
        return medicineRepo.findAll();
    }

    @Override
    public Batch verifyByQrCode(String qrData) {
        return batchRepo.findByQrCodeData(qrData)
                .orElseThrow(() -> new RuntimeException("Invalid QR Code or Counterfeit Drug!"));
    }
}
