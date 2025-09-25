package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.model.DataFile;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * The type Child alert service test.
 */
@ExtendWith(MockitoExtension.class)
class ChildAlertServiceTest {

    @Mock
    private DataRepository repo;

    @InjectMocks
    private ChildAlertService service;

    private DataFile dataFile;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        // Création d'un DataFile fictif
        dataFile = new DataFile();

        // Ajout des personnes
        Person child = new Person();
        child.setFirstName("John");
        child.setLastName("Doe");
        child.setAddress("1509 Culver St");
        dataFile.getPersons().add(child);

        Person adult = new Person();
        adult.setFirstName("Jane");
        adult.setLastName("Doe");
        adult.setAddress("1509 Culver St");
        dataFile.getPersons().add(adult);

        // Ajout des dossiers médicaux
        MedicalRecord mrChild = new MedicalRecord();
        mrChild.setFirstName("John");
        mrChild.setLastName("Doe");
        mrChild.setBirthdate("05/05/2015"); // Enfant
        dataFile.getMedicalRecords().add(mrChild);

        MedicalRecord mrAdult = new MedicalRecord();
        mrAdult.setFirstName("Jane");
        mrAdult.setLastName("Doe");
        mrAdult.setBirthdate("05/05/1990"); // Adulte
        dataFile.getMedicalRecords().add(mrAdult);

        // Mock du repo
        when(repo.getDataFile()).thenReturn(dataFile);
    }

    /**
     * Gets children by address should return child with household members.
     */
    @Test
    void getChildrenByAddress_shouldReturnChildWithHouseholdMembers() {
        List<ChildAlertDTO> result = service.getChildrenByAddress("1509 Culver St");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        assertThat(result.get(0).getHouseholdMembers()).containsExactly("Jane Doe");
    }

    /**
     * Gets children by address should return empty list when no children.
     */
    @Test
    void getChildrenByAddress_shouldReturnEmptyList_whenNoChildren() {
        // Changer la date de naissance pour rendre tout le monde adulte
        dataFile.getMedicalRecords().forEach(mr -> mr.setBirthdate("01/01/1980"));

        List<ChildAlertDTO> result = service.getChildrenByAddress("1509 Culver St");

        assertThat(result).isEmpty();
    }
}
