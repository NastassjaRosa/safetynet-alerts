package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final DataRepository repo;

    public void addMedicalRecord(MedicalRecord record) {
        repo.getDataFile().getMedicalRecords().add(record);
        log.info("MedicalRecord added: {} {}", record.getFirstName(), record.getLastName());
    }

    public boolean updateMedicalRecord(MedicalRecord record) {
        return repo.getDataFile().getMedicalRecords()
                .stream()
                .filter(mr -> mr.getFirstName().equalsIgnoreCase(record.getFirstName()) &&
                        mr.getLastName().equalsIgnoreCase(record.getLastName()))
                .findFirst()
                .map(existing -> {
                    existing.setBirthdate(record.getBirthdate());
                    existing.setMedications(record.getMedications());
                    existing.setAllergies(record.getAllergies());
                    log.info("MedicalRecord updated: {} {}", record.getFirstName(), record.getLastName());
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        boolean removed = repo.getDataFile().getMedicalRecords()
                .removeIf(mr -> mr.getFirstName().equalsIgnoreCase(firstName) &&
                        mr.getLastName().equalsIgnoreCase(lastName));
        if (removed) {
            log.info("MedicalRecord deleted: {} {}", firstName, lastName);
        }
        return removed;
    }


}
