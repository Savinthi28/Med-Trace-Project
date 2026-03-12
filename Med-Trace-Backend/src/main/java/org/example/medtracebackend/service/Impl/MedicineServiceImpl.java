package org.example.medtracebackend.service.Impl;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.medtracebackend.entity.Batch;
import org.example.medtracebackend.entity.Medicine;
import org.example.medtracebackend.repository.BatchRepo;
import org.example.medtracebackend.repository.MedicineRepo;
import org.example.medtracebackend.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
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

    @Override
    public Medicine getMedicineById(Long id) {
        return medicineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
    }

    @Override
    public Medicine updateMedicine(Long id, Medicine medicineDetails) {
        Medicine existingMedicine = getMedicineById(id);

        existingMedicine.setName(medicineDetails.getName());
        existingMedicine.setManufacturer(medicineDetails.getManufacturer());
        existingMedicine.setCategory(medicineDetails.getCategory());

        return medicineRepo.save(existingMedicine);
    }

    @Override
    public void deleteMedicine(Long id) {
        medicineRepo.deleteById(id);
    }


    @Override
    public List<Batch> getAllBatches() {
        return batchRepo.findAll();
    }

    @Override
    public void deleteBatch(Long id) {
        batchRepo.deleteById(id);
    }

    @Override
    public byte[] generateExpiryReport() {
        List<Batch> expiredBatches = batchRepo.findAll().stream()
                .filter(b -> "EXPIRED".equals(b.getStatus()))
                .toList();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            document.add(new Paragraph("Med-Trace: Expired Medicines Report", font));
            document.add(new Paragraph("Date: " + LocalDate.now()));
            document.add(new Paragraph("--------------------------------------------"));

            for (Batch b : expiredBatches) {
                document.add(new Paragraph("Batch: " + b.getBatchNumber() +
                        " | Medicine: " + b.getMedicine().getName() +
                        " | Expired On: " + b.getExpiryDate()));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
