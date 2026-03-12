package org.example.medtracebackend.controller;

import org.example.medtracebackend.entity.Batch;
import org.example.medtracebackend.entity.Medicine;
import org.example.medtracebackend.service.MedicineService;
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

    @PostMapping
    public Medicine createMedicine(@RequestBody Medicine medicine){
        return medicineService.saveMedicine(medicine);
    }
    @PostMapping("/{id}/batches")
    public Batch addBatch(@PathVariable Long id, @RequestBody Batch batch){
        return medicineService.addBatchToMedicine(id, batch);
    }
    @GetMapping
    public List<Medicine> getAll(){
        return medicineService.getAllMedicine();
    }
    @GetMapping("/verify/{qrData}")
    public Batch verify(@PathVariable String qrData){
        return medicineService.verifyByQrCode(qrData);
    }

    @GetMapping("/{id}")
    public Medicine getMedicineById(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }

    @PutMapping("/{id}")
    public Medicine updateMedicine(@PathVariable Long id, @RequestBody Medicine medicine) {
        return medicineService.updateMedicine(id, medicine);
    }

    @DeleteMapping("/{id}")
    public String deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return "Medicine with ID " + id + " has been deleted successfully!";
    }

    @GetMapping("/batches/all")
    public List<Batch> getAllBatches() {
        return medicineService.getAllBatches();
    }

    @DeleteMapping("/batches/{id}")
    public String deleteBatch(@PathVariable Long id) {
        medicineService.deleteBatch(id);
        return "Batch with ID " + id + " deleted successfully!";
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
