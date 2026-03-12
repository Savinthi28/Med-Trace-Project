package org.example.medtracebackend.service;

import org.example.medtracebackend.entity.Batch;
import org.example.medtracebackend.entity.Medicine;

import java.util.List;

public interface MedicineService {
    Medicine saveMedicine(Medicine medicine);
    Batch addBatchToMedicine(Long medicineId, Batch batch);
    List<Medicine> getAllMedicine();
    Batch verifyByQrCode(String qrData);

    Medicine getMedicineById(Long id);
    Medicine updateMedicine(Long id, Medicine medicineDetails);
    void deleteMedicine(Long id);

    List<Batch> getAllBatches();
    void deleteBatch(Long id);

    byte[] generateExpiryReport();
}
