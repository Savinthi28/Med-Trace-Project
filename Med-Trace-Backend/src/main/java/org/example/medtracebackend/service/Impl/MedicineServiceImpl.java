package org.example.medtracebackend.service.Impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.medtracebackend.dto.BatchDTO;
import org.example.medtracebackend.dto.MedicineDTO;
import org.example.medtracebackend.entity.Batch;
import org.example.medtracebackend.entity.Medicine;
import org.example.medtracebackend.exception.ResourceNotFoundException;
import org.example.medtracebackend.repository.BatchRepo;
import org.example.medtracebackend.repository.MedicineRepo;
import org.example.medtracebackend.service.MedicineService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MedicineDTO saveMedicine(Medicine medicine) {
        return modelMapper.map(medicineRepo.save(medicine), MedicineDTO.class);
    }

    @Override
    public BatchDTO addBatchToMedicine(Long medicineId, Batch batch) {
        Medicine medicine = medicineRepo.findById(medicineId)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));
        batch.setMedicine(medicine);
        return modelMapper.map(batchRepo.save(batch), BatchDTO.class);
    }

    @Override
    public List<MedicineDTO> getAllMedicine() {
        return modelMapper.map(medicineRepo.findAll(), new TypeToken<List<MedicineDTO>>() {}.getType());
    }

    @Override
    public BatchDTO verifyByQrCode(String qrData) {
        Batch batch = batchRepo.findByQrCodeData(qrData)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid QR Code!"));
        return modelMapper.map(batch, BatchDTO.class);
    }

    @Override
    public MedicineDTO getMedicineById(Long id) {
        Medicine medicine = medicineRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found: " + id));
        return modelMapper.map(medicine, MedicineDTO.class);
    }

    @Override
    public MedicineDTO updateMedicine(Long id, Medicine details) {
        Medicine existing = medicineRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found: " + id));
        existing.setName(details.getName());
        existing.setManufacturer(details.getManufacturer());
        existing.setCategory(details.getCategory());
        return modelMapper.map(medicineRepo.save(existing), MedicineDTO.class);
    }

    @Override
    public void deleteMedicine(Long id) {
        medicineRepo.deleteById(id);
    }

    @Override
    public List<BatchDTO> getAllBatches() {
        return modelMapper.map(batchRepo.findAll(), new TypeToken<List<BatchDTO>>() {}.getType());
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
                        " | Medicine: " + (b.getMedicine() != null ? b.getMedicine().getName() : "N/A") +
                        " | Expired On: " + b.getExpiryDate()));
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}