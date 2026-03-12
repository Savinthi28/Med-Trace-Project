package org.example.medtracebackend.service;

import org.example.medtracebackend.dto.BatchDTO;
import org.example.medtracebackend.dto.MedicineDTO;
import org.example.medtracebackend.entity.Batch;
import org.example.medtracebackend.entity.Medicine;
import java.util.List;

public interface MedicineService {
    MedicineDTO saveMedicine(Medicine medicine);
    BatchDTO addBatchToMedicine(Long medicineId, Batch batch);

    List<MedicineDTO> getAllMedicine();
    BatchDTO verifyByQrCode(String qrData);

    MedicineDTO getMedicineById(Long id);
    MedicineDTO updateMedicine(Long id, Medicine medicineDetails);
    void deleteMedicine(Long id);

    List<BatchDTO> getAllBatches();
    void deleteBatch(Long id);

    byte[] generateExpiryReport();
}