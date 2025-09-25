package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.model.DataFile;
import com.safetynet.safetynetalerts.model.FireStation;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * The type Fire service test.
 */
@ExtendWith(MockitoExtension.class)
class FireServiceTest {

    @Mock
    private DataRepository repo;

    @InjectMocks
    private FireService service;

    private DataFile dataFile;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        dataFile = new DataFile();
        dataFile.setPersons(new ArrayList<>());
        dataFile.setFireStations(new ArrayList<>());
        dataFile.setMedicalRecords(new ArrayList<>());

        // Caserne pour l'adresse
        FireStation fs = new FireStation();
        fs.setAddress("1509 Culver St");
        fs.setStation(3);
        dataFile.getFireStations().add(fs);

        // Personne avec dossier médical
        Person p1 = new Person();
        p1.setFirstName("John");
        p1.setLastName("Boyd");
        p1.setAddress("1509 Culver St");
        p1.setPhone("123-456");
        dataFile.getPersons().add(p1);

        MedicalRecord mr = new MedicalRecord();
        mr.setFirstName("John");
        mr.setLastName("Boyd");
        mr.setBirthdate("03/06/1984");
        mr.setMedications(List.of("med1"));
        mr.setAllergies(List.of("allergy1"));
        dataFile.getMedicalRecords().add(mr);

        when(repo.getDataFile()).thenReturn(dataFile);
    }

    /**
     * Gets persons by address should return fire dto.
     */
    @Test
    void getPersonsByAddress_shouldReturnFireDTO() {
        FireDTO result = service.getPersonsByAddress("1509 Culver St");

        assertThat(result.getStationNumber()).isEqualTo(3);
        assertThat(result.getPersons()).hasSize(1);
        assertThat(result.getPersons().get(0).getFirstName()).isEqualTo("John");
    }

    /**
     * Gets persons by address should return empty list when no residents.
     */
    @Test
    void getPersonsByAddress_shouldReturnEmptyListWhenNoResidents() {
        dataFile.getPersons().clear();
        FireDTO result = service.getPersonsByAddress("1509 Culver St");

        assertThat(result.getStationNumber()).isEqualTo(3);
        assertThat(result.getPersons()).isEmpty();
    }

    /**
     * Gets persons by address should return minus one when address not found.
     */
    @Test
    void getPersonsByAddress_shouldReturnMinusOneWhenAddressNotFound() {
        FireDTO result = service.getPersonsByAddress("Unknown");

        assertThat(result.getStationNumber()).isEqualTo(-1);
        assertThat(result.getPersons()).isEmpty();
    }
}
