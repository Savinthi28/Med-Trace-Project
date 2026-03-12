package org.example.medtracebackend.controller;

import org.example.medtracebackend.dto.BatchDTO;
import org.example.medtracebackend.dto.MedicineDTO;
import org.example.medtracebackend.entity.Batch;
import org.example.medtracebackend.entity.Medicine;
import org.example.medtracebackend.service.MedicineService;
import org.example.medtracebackend.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    // 1. Create Medicine
    @PostMapping
    public APIResponse<MedicineDTO> createMedicine(@RequestBody Medicine medicine){
        MedicineDTO saved = medicineService.saveMedicine(medicine);
        return new APIResponse<>(201, "Medicine created successfully", saved);
    }

    // 2. Add Batch
    @PostMapping("/{id}/batches")
    public APIResponse<BatchDTO> addBatch(@PathVariable Long id, @RequestBody Batch batch){
        BatchDTO savedBatch = medicineService.addBatchToMedicine(id, batch);
        return new APIResponse<>(201, "Batch added successfully", savedBatch);
    }

    // 3. Get All Medicines
    @GetMapping
    public APIResponse<List<MedicineDTO>> getAll(){
        List<MedicineDTO> list = medicineService.getAllMedicine();
        return new APIResponse<>(200, "All medicines fetched", list);
    }

    // 4. Verify QR
    @GetMapping("/verify/{qrData}")
    public APIResponse<BatchDTO> verify(@PathVariable String qrData){
        BatchDTO batch = medicineService.verifyByQrCode(qrData);
        return new APIResponse<>(200, "Verification successful", batch);
    }

    // 5. Get Medicine By ID
    @GetMapping("/{id}")
    public APIResponse<MedicineDTO> getMedicineById(@PathVariable Long id) {
        MedicineDTO medicine = medicineService.getMedicineById(id);
        return new APIResponse<>(200, "Medicine found", medicine);
    }

    // 6. Update Medicine
    @PutMapping("/{id}")
    public APIResponse<MedicineDTO> updateMedicine(@PathVariable Long id, @RequestBody Medicine medicine) {
        MedicineDTO updated = medicineService.updateMedicine(id, medicine);
        return new APIResponse<>(200, "Medicine updated successfully", updated);
    }

    // 7. Delete Medicine
    @DeleteMapping("/{id}")
    public APIResponse<String> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return new APIResponse<>(200, "Medicine deleted", "ID: " + id);
    }

    // 8. Get All Batches
    @GetMapping("/batches/all")
    public APIResponse<List<BatchDTO>> getAllBatches() {
        List<BatchDTO> batches = medicineService.getAllBatches();
        return new APIResponse<>(200, "All batches fetched", batches);
    }

    // 9. Delete Batch
    @DeleteMapping("/batches/{id}")
    public APIResponse<String> deleteBatch(@PathVariable Long id) {
        medicineService.deleteBatch(id);
        return new APIResponse<>(200, "Batch deleted", "ID: " + id);
    }

    @GetMapping("/report/expiry")
    public ResponseEntity<byte[]> downloadReport() {
        byte[] pdfContents = medicineService.generateExpiryReport();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "expiry-report.pdf");
        return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
    }
}