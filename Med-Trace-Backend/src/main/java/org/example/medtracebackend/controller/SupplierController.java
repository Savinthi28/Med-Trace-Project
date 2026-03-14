package org.example.medtracebackend.controller;

import org.example.medtracebackend.dto.SupplierDTO;
import org.example.medtracebackend.service.SupplierService;
import org.example.medtracebackend.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<APIResponse<SupplierDTO>> save(@RequestBody SupplierDTO dto) {
        SupplierDTO saved = supplierService.saveSupplier(dto);
        return new ResponseEntity<>(new APIResponse<>(201, "Supplier saved successfully", saved), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<APIResponse<List<SupplierDTO>>> getAll() {
        List<SupplierDTO> list = supplierService.getAllSuppliers();
        return new ResponseEntity<>(new APIResponse<>(200, "All Supplier successfully", list), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SupplierDTO>> getById(@PathVariable Long id) {
        SupplierDTO supplier = supplierService.getSupplierById(id);
        return new ResponseEntity<>(new APIResponse<>(200, "Supplier found", supplier), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<SupplierDTO>> update(@PathVariable Long id, @RequestBody SupplierDTO dto) {
        SupplierDTO updated = supplierService.updateSupplier(id, dto);
        return new ResponseEntity<>(new APIResponse<>(200, "Supplier updated successfully", updated), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>(new APIResponse<>(200,"Supplier deleted", "ID: " + id), HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<SupplierDTO>>> search(@RequestParam String name) {
        List<SupplierDTO> results = supplierService.searchSuppliers(name);
        return new ResponseEntity<>(new APIResponse<>(200, "Search results ", results), HttpStatus.OK);
    }
}
