package org.example.medtracebackend.repository;

import org.example.medtracebackend.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepo extends JpaRepository<Medicine, Long> {
}
