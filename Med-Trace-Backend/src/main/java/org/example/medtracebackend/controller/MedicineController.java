package org.example.medtracebackend.controller;

import org.example.medtracebackend.entity.Batch;
import org.example.medtracebackend.entity.Medicine;
import org.example.medtracebackend.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
