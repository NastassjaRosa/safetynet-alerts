package com.safetynet.safetynetalerts.controller;


import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Medical record controller.
 */
@Slf4j
@RestController
@RequestMapping("/medicalRecord")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService service;

    /**
     * POST – ajout d'un dossier médical
     *
     * @param medicalRecord the medical record
     * @return the response entity
     */

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody MedicalRecord medicalRecord) {
        try {
            service.addMedicalRecord(medicalRecord);
            log.info("MedicalRecord created {}", medicalRecord);
            return ResponseEntity.status(201).build(); // 201 Created
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("already exists")) {
                log.warn("Doublon dossier médical: {}", e.getMessage());
                return ResponseEntity.status(409).build(); // 409 Conflict
            } else if (e.getMessage().contains("Person not found")) {
                log.warn("Personne introuvable: {}", e.getMessage());
                return ResponseEntity.badRequest().build(); // 400 Bad Request
            } else {
                return ResponseEntity.internalServerError().build(); // 500 si autre problème
            }
        }
    }


    /**
     * PUT – mise à jour d'un dossier médical existant
     *
     * @param medicalRecord the medical record
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody MedicalRecord medicalRecord) {
        boolean updated = service.updateMedicalRecord(medicalRecord);
        if (updated) {
            log.info("MedicalRecord updated {}", medicalRecord);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            log.warn("MedicalRecord to update not found {}", medicalRecord);
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    /**
     * DELETE – suppression d'un dossier médical par prénom + nom
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the response entity
     */
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String firstName,
                                       @RequestParam String lastName) {
        boolean removed = service.deleteMedicalRecord(firstName, lastName);
        if (removed) {
            log.info("MedicalRecord deleted {} {}", firstName, lastName);
            return ResponseEntity.noContent().build();
        }
        log.warn("MedicalRecord to delete not found {} {}", firstName, lastName);
        return ResponseEntity.notFound().build();
    }
}
