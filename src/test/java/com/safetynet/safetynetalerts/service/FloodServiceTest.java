package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.FloodDTO;
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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * The type Flood service test.
 */
@ExtendWith(MockitoExtension.class)
class FloodServiceTest {

    @Mock
    private DataRepository repo;

    @InjectMocks
    private FloodService service;

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

        // Ajout station
        FireStation fs = new FireStation();
        fs.setAddress("1509 Culver St");
        fs.setStation(1);
        dataFile.getFireStations().add(fs);

        // Ajout personne
        Person p1 = new Person();
        p1.setFirstName("John");
        p1.setLastName("Boyd");
        p1.setAddress("1509 Culver St");
        p1.setPhone("123-456");
        dataFile.getPersons().add(p1);

        // Ajout dossier médical
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
     * Gets households by stations should return households.
     */
    @Test
    void getHouseholdsByStations_shouldReturnHouseholds() {
        FloodDTO result = service.getHouseholdsByStations(List.of(1));

        assertThat(result.getHouseholds()).containsKey("1509 Culver St");
        assertThat(result.getHouseholds().get("1509 Culver St")).hasSize(1);
        assertThat(result.getHouseholds().get("1509 Culver St").get(0).getFirstName())
                .isEqualTo("John");
    }

    /**
     * Gets households by stations should return empty when no stations.
     */
    @Test
    void getHouseholdsByStations_shouldReturnEmptyWhenNoStations() {
        FloodDTO result = service.getHouseholdsByStations(List.of(99));

        assertThat(result.getHouseholds()).isEmpty();
    }

    /**
     * Gets households by stations should return empty when no persons.
     */
    @Test
    void getHouseholdsByStations_shouldReturnEmptyWhenNoPersons() {
        dataFile.getPersons().clear();
        FloodDTO result = service.getHouseholdsByStations(List.of(1));

        assertThat(result.getHouseholds()).doesNotContainKey("1509 Culver St");

    }
}
