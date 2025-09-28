package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DataFile;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * The type Medical record service test.
 */
@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    @Mock
    private DataRepository repo;

    @InjectMocks
    private MedicalRecordService service;

    private DataFile dataFile;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        dataFile = new DataFile();
        dataFile.setMedicalRecords(new ArrayList<>());

        when(repo.getDataFile()).thenReturn(dataFile);
    }

    /**
     * Add medical record should add record.
     */
    @Test
    void addMedicalRecord_shouldAddRecord() {
        Person john = new Person();
        john.setFirstName("John");
        john.setLastName("Doe");
        john.setAddress("1509 Culver St");
        john.setCity("Culver");
        john.setZip(97451);
        john.setPhone("841-874-6512");
        john.setEmail("john.doe@email.com");
        dataFile.getPersons().add(john);

        MedicalRecord record = new MedicalRecord();
        record.setFirstName("John");
        record.setLastName("Doe");

        service.addMedicalRecord(record);

        assertThat(dataFile.getMedicalRecords()).contains(record);
    }

    /**
     * Update medical record should return true when record exists.
     */
    @Test
    void updateMedicalRecord_shouldReturnTrueWhenRecordExists() {
        MedicalRecord record = new MedicalRecord();
        record.setFirstName("Jane");
        record.setLastName("Doe");
        dataFile.getMedicalRecords().add(record);

        MedicalRecord updated = new MedicalRecord();
        updated.setFirstName("Jane");
        updated.setLastName("Doe");
        updated.setBirthdate("01/01/2000");

        boolean result = service.updateMedicalRecord(updated);

        assertThat(result).isTrue();
        assertThat(dataFile.getMedicalRecords().get(0).getBirthdate()).isEqualTo("01/01/2000");
    }

    /**
     * Update medical record should return false when record does not exist.
     */
    @Test
    void updateMedicalRecord_shouldReturnFalseWhenRecordDoesNotExist() {
        MedicalRecord updated = new MedicalRecord();
        updated.setFirstName("Unknown");
        updated.setLastName("Person");

        boolean result = service.updateMedicalRecord(updated);

        assertThat(result).isFalse();
    }

    /**
     * Delete medical record should return true when record exists.
     */
    @Test
    void deleteMedicalRecord_shouldReturnTrueWhenRecordExists() {
        MedicalRecord record = new MedicalRecord();
        record.setFirstName("Mike");
        record.setLastName("Smith");
        dataFile.getMedicalRecords().add(record);

        boolean result = service.deleteMedicalRecord("Mike", "Smith");

        assertThat(result).isTrue();
        assertThat(dataFile.getMedicalRecords()).isEmpty();
    }

    /**
     * Delete medical record should return false when record does not exist.
     */
    @Test
    void deleteMedicalRecord_shouldReturnFalseWhenRecordDoesNotExist() {
        boolean result = service.deleteMedicalRecord("Ghost", "Person");

        assertThat(result).isFalse();
    }
}
