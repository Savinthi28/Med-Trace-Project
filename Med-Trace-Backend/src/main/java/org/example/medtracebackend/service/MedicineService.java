package org.example.medtracebackend.service;

import org.example.medtracebackend.entity.Batch;
import org.example.medtracebackend.entity.Medicine;

import java.util.List;

public interface MedicineService {
    Medicine saveMedicine(Medicine medicine);
    Batch addBatchToMedicine(Long medicineId, Batch batch);
    List<Medicine> getAllMedicine();
    Batch verifyByQrCode(String qrData);
}
