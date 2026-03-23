package org.example.medtracebackend.service.Impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
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
@Transactional
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepo medicineRepo;

    @Autowired
    private BatchRepo batchRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MedicineDTO saveMedicine(MedicineDTO medicineDTO) {
        if (medicineDTO == null) throw new NullPointerException("Medicine DTO cannot be null");
        Medicine medicineEntity = modelMapper.map(medicineDTO, Medicine.class);
        Medicine savedMedicine = medicineRepo.save(medicineEntity);

        if (medicineDTO.getInitialBatch() != null) {
            BatchDTO batchDTO = medicineDTO.getInitialBatch();
            Batch batchEntity = modelMapper.map(batchDTO, Batch.class);

            if (batchEntity.getQrCodeData() == null) {
                batchEntity.setQrCodeData(batchEntity.getBatchNumber());
            }
            if (batchEntity.getStatus() == null) {
                batchEntity.setStatus("AVAILABLE");
            }

            batchEntity.setMedicine(savedMedicine);
            batchRepo.save(batchEntity);
        }
        return modelMapper.map(savedMedicine, MedicineDTO.class);
    }

    @Override
    public BatchDTO addBatchToMedicine(Long medicineId, BatchDTO batchDTO) {
        Medicine medicine = medicineRepo.findById(medicineId)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));

        Batch batchEntity = modelMapper.map(batchDTO, Batch.class);
        batchEntity.setMedicine(medicine);
        return modelMapper.map(batchRepo.save(batchEntity), BatchDTO.class);
    }

    @Override
    public List<MedicineDTO> getAllMedicine() {
        return modelMapper.map(medicineRepo.findAll(), new TypeToken<List<MedicineDTO>>() {}.getType());
    }

    @Override
    public BatchDTO verifyByQrCode(String qrData) {
        Batch batch = batchRepo.findByQrCodeData(qrData)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid QR Code!"));

        BatchDTO dto = modelMapper.map(batch, BatchDTO.class);
        dto.setMedicineName(batch.getMedicine().getName());

        if (batch.getExpiryDate().isBefore(LocalDate.now())) {
            dto.setStatus("EXPIRED");
        } else {
            dto.setStatus("VERIFIED");
        }

        return dto;
    }

    @Override
    public MedicineDTO getMedicineById(Long id) {
        Medicine medicine = medicineRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found: " + id));
        return modelMapper.map(medicine, MedicineDTO.class);
    }

    @Override
    public MedicineDTO updateMedicine(Long id, MedicineDTO details) {
        Medicine existing = medicineRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found: " + id));

        existing.setName(details.getName());
        existing.setManufacturer(details.getManufacturer());
        existing.setCategory(details.getCategory());

        if (details.getInitialBatch() != null) {
            if (existing.getBatches() != null && !existing.getBatches().isEmpty()) {
                Batch lastBatch = existing.getBatches().get(existing.getBatches().size() - 1);
                lastBatch.setBatchNumber(details.getInitialBatch().getBatchNumber());
                lastBatch.setStockQuantity(details.getInitialBatch().getStockQuantity());
                lastBatch.setExpiryDate(details.getInitialBatch().getExpiryDate());
                batchRepo.save(lastBatch);
            }
        }

        return modelMapper.map(medicineRepo.save(existing), MedicineDTO.class);
    }

    @Override
    public void deleteMedicine(Long id) {
        if(!medicineRepo.existsById(id)) throw new ResourceNotFoundException("Medicine not found");
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
                .filter(b -> b.getExpiryDate() != null && b.getExpiryDate().isBefore(LocalDate.now()))
                .toList();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Med-Trace: Expired Medicines Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("Date: " + LocalDate.now()));
            document.add(new Paragraph(" "));

            for (Batch b : expiredBatches) {
                document.add(new Paragraph("Batch: " + b.getBatchNumber() +
                        " | Medicine: " + (b.getMedicine() != null ? b.getMedicine().getName() : "N/A") +
                        " | Expired On: " + b.getExpiryDate()));
            }
            document.close();
        } catch (Exception e) { e.printStackTrace(); }
        return out.toByteArray();
    }

    @Override
    public long getTotalMedicineCount() {
        return medicineRepo.count();
    }

    @Override
    public List<BatchDTO> getExpiredBatchesList() {
        List<Batch> expired = batchRepo.findAll().stream()
                .filter(b -> b.getExpiryDate() != null && b.getExpiryDate().isBefore(LocalDate.now()))
                .toList();
        return modelMapper.map(expired, new TypeToken<List<BatchDTO>>() {}.getType());
    }
}