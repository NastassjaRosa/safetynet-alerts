package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.safetynet.safetynetalerts.util.SaveUtil.save;

/**
 * The type Medical record service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final DataRepository repo;


    /**
     * Add medical record.
     *
     * @param record the record
     */
    public void addMedicalRecord(MedicalRecord record) {
        repo.getDataFile().getMedicalRecords().add(record);
        save(repo, "ajout du dossier médical");
        log.debug("Dossier médical ajouté : {} {}", record.getFirstName(), record.getLastName());
    }

    /**
     * Update medical record boolean.
     *
     * @param record the record
     * @return the boolean
     */
    public boolean updateMedicalRecord(MedicalRecord record) {
        boolean updated = repo.getDataFile().getMedicalRecords()
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

        if (updated) {
            save(repo, "mise à jour du dossier médical");
            log.debug("Dossier médical mis à jour : {} {}", record.getFirstName(), record.getLastName());
        }
        return updated;

    }

    /**
     * Delete medical record boolean.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the boolean
     */
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        boolean removed = repo.getDataFile().getMedicalRecords()
                .removeIf(mr -> mr.getFirstName().equalsIgnoreCase(firstName) &&
                        mr.getLastName().equalsIgnoreCase(lastName));
        if (removed) {
            save(repo, "suppression du dossier médical");
            log.debug("Dossier médical supprimé : {} {}", firstName, lastName);
        }
        return removed;
    }


}
