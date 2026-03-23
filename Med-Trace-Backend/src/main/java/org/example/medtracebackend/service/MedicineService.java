package org.example.medtracebackend.service;

import org.example.medtracebackend.dto.BatchDTO;
import org.example.medtracebackend.dto.MedicineDTO;
import java.util.List;

public interface MedicineService {
    MedicineDTO saveMedicine(MedicineDTO medicineDTO);
    BatchDTO addBatchToMedicine(Long medicineId, BatchDTO batchDTO);
    List<MedicineDTO> getAllMedicine();
    BatchDTO verifyByQrCode(String qrData);
    MedicineDTO getMedicineById(Long id);
    MedicineDTO updateMedicine(Long id, MedicineDTO medicineDTO);
    void deleteMedicine(Long id);
    List<BatchDTO> getAllBatches();
    void deleteBatch(Long id);
    byte[] generateExpiryReport();
    long getTotalMedicineCount();
    List<BatchDTO> getExpiredBatchesList();
}