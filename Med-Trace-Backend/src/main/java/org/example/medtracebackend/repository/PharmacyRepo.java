package org.example.medtracebackend.repository;

import org.example.medtracebackend.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyRepo extends JpaRepository<Pharmacy, Long> {
    List<Pharmacy> findByLocationContainingIgnoreCase(String location);
}
