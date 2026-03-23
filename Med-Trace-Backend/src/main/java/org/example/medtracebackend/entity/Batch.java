package org.example.medtracebackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String batchNumber;

    @Column(length = 500)
    private String qrCodeData;

    private LocalDate expiryDate;
    private int stockQuantity;
    private String status;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    @JsonBackReference
    private Medicine medicine;
}
