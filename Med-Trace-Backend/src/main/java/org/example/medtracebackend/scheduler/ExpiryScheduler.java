package org.example.medtracebackend.scheduler;

import org.example.medtracebackend.entity.Batch;
import org.example.medtracebackend.repository.BatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ExpiryScheduler {

    @Autowired
    private BatchRepo batchRepo;

    @Scheduled(fixedRate = 60000)
    public void checkExpiredMedicines() {
        System.out.println("⏳ System Auto-Check: Checking for expired medicines... Date: " + LocalDate.now());

        List<Batch> allBatches = batchRepo.findAll();
        LocalDate today = LocalDate.now();

        for (Batch batch : allBatches) {
            if (batch.getExpiryDate().isBefore(today) && !batch.getStatus().equals("EXPIRED")) {
                batch.setStatus("EXPIRED");
                batchRepo.save(batch);
                System.out.println("ALERT: Batch " + batch.getBatchNumber() + " is now EXPIRED!");
            }
        }
    }
}
