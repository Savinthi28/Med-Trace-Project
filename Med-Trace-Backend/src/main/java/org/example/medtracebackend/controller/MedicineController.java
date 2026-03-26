package org.example.medtracebackend.controller;

import org.example.medtracebackend.dto.BatchDTO;
import org.example.medtracebackend.dto.MedicineDTO;
import org.example.medtracebackend.service.MedicineService;
import org.example.medtracebackend.service.ScanHistoryService;
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

    @Autowired
    private ScanHistoryService scanHistoryService; // අලුතින් එක් කළ සර්විස් එක

    @PostMapping
    public ResponseEntity<APIResponse<MedicineDTO>> createMedicine(@RequestBody MedicineDTO medicineDTO){
        MedicineDTO saved = medicineService.saveMedicine(medicineDTO);
        return new ResponseEntity<>(new APIResponse<>(201, "Medicine created successfully", saved), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/batches")
    public ResponseEntity<APIResponse<BatchDTO>> addBatch(@PathVariable Long id, @RequestBody BatchDTO batchDTO){
        BatchDTO savedBatch = medicineService.addBatchToMedicine(id, batchDTO);
        return new ResponseEntity<>(new APIResponse<>(201, "Batch added successfully", savedBatch), HttpStatus.CREATED);
    }

    // --- වැදගත්ම කොටස: VERIFY WITH HISTORY ---
    @GetMapping("/verify/{qrData}/{userId}")
    public ResponseEntity<APIResponse<BatchDTO>> verify(@PathVariable String qrData, @PathVariable Long userId){
        // 1. QR එකෙන් බෙහෙත හොයනවා
        BatchDTO batch = medicineService.verifyByQrCode(qrData);

        if (batch != null) {
            // 2. ඉතිහාසයට (History) ස්වයංක්‍රීයව එක් කරනවා
            scanHistoryService.saveScanRecord(userId, batch.getId(), "VERIFIED");
            return new ResponseEntity<>(new APIResponse<>(200, "Verification successful", batch), HttpStatus.OK);
        }

        return new ResponseEntity<>(new APIResponse<>(404, "Invalid Medicine", null), HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<MedicineDTO>>> getAll(){
        List<MedicineDTO> list = medicineService.getAllMedicine();
        return new ResponseEntity<>(new APIResponse<>(200, "All medicines fetched", list), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<MedicineDTO>> getMedicineById(@PathVariable Long id) {
        MedicineDTO medicine = medicineService.getMedicineById(id);
        return new ResponseEntity<>(new APIResponse<>(200, "Medicine found", medicine), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<MedicineDTO>> updateMedicine(@PathVariable Long id, @RequestBody MedicineDTO medicineDTO) {
        MedicineDTO updated = medicineService.updateMedicine(id, medicineDTO);
        return new ResponseEntity<>(new APIResponse<>(200, "Medicine updated successfully", updated), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return new ResponseEntity<>(new APIResponse<>(200, "Medicine deleted", "ID: " + id), HttpStatus.OK);
    }

    @GetMapping("/batches/all")
    public ResponseEntity<APIResponse<List<BatchDTO>>> getAllBatches() {
        List<BatchDTO> batches = medicineService.getAllBatches();
        return new ResponseEntity<>(new APIResponse<>(200, "All batches fetched", batches), HttpStatus.OK);
    }

    @DeleteMapping("/batches/{id}")
    public ResponseEntity<APIResponse<String>> deleteBatch(@PathVariable Long id) {
        medicineService.deleteBatch(id);
        return new ResponseEntity<>(new APIResponse<>(200, "Batch deleted", "ID: " + id), HttpStatus.OK);
    }

    @GetMapping("/report/expiry")
    public ResponseEntity<byte[]> downloadReport() {
        byte[] pdfContents = medicineService.generateExpiryReport();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "expiry-report.pdf");
        return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<APIResponse<Long>> getCount() {
        return new ResponseEntity<>(new APIResponse<>(200, "Total Count", medicineService.getTotalMedicineCount()), HttpStatus.OK);
    }

    @GetMapping("/expired/list")
    public ResponseEntity<APIResponse<List<BatchDTO>>> getExpiredList() {
        return new ResponseEntity<>(new APIResponse<>(200, "Expired Batches", medicineService.getExpiredBatchesList()), HttpStatus.OK);
    }
}