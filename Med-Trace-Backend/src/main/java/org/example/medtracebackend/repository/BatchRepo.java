package org.example.medtracebackend.repository;

import org.example.medtracebackend.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BatchRepo extends JpaRepository<Batch, Long> {

    Optional<Batch> findByQrCodeData(String qrCodeData);
}