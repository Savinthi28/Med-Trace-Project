package org.example.medtracebackend.controller;

import org.example.medtracebackend.dto.PharmacyDTO;
import org.example.medtracebackend.service.PharmacyService;
import org.example.medtracebackend.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacies")
@CrossOrigin(origins = "*")
public class PharmacyController {
    @Autowired
    private PharmacyService pharmacyService;

    @PostMapping
    public ResponseEntity<APIResponse<PharmacyDTO>> save(@RequestBody PharmacyDTO dto) {
        return new ResponseEntity<>(new APIResponse<>(201, "Pharmacy Registered", pharmacyService.savePharmacy(dto)), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<APIResponse<List<PharmacyDTO>>> getAll() {
        return new ResponseEntity<>(new APIResponse<>(200, "Success", pharmacyService.getAllPharmacies()), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<PharmacyDTO>> getById(@PathVariable Long id) {
        return new ResponseEntity<>(new APIResponse<>(200, "Found", pharmacyService.getPharmacyById(id)), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<PharmacyDTO>> update(@PathVariable Long id, @RequestBody PharmacyDTO dto) {
        return new ResponseEntity<>(new APIResponse<>(200, "Update Successful", pharmacyService.updatePharmacy(id, dto)), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable Long id) {
        pharmacyService.deletePharmacy(id);
        return new ResponseEntity<>(new APIResponse<>(200, " Delete Successful", " ID:" + id), HttpStatus.OK);
    }
    @GetMapping("/details/{search}")
    public ResponseEntity<APIResponse<List<PharmacyDTO>>> searchByLocation(@RequestParam String location) {
        return new ResponseEntity<>(new APIResponse<>(200, "Search Results", pharmacyService.getPharmaciesByLocation(location)), HttpStatus.OK);
    }
    @GetMapping("/count")
    public ResponseEntity<APIResponse<Long>> getPharmacyCount() {
        return new ResponseEntity<>(new APIResponse<>(200, "Success", pharmacyService.getPharmacyCount()), HttpStatus.OK);
    }
}
