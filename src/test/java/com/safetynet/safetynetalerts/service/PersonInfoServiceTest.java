package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * The type Person info service test.
 */
@ExtendWith(MockitoExtension.class)
class PersonInfoServiceTest {

    @Mock
    private DataRepository repo;

    @InjectMocks
    private PersonInfoService service;

    private DataFile dataFile;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        dataFile = new DataFile();
        dataFile.setPersons(new ArrayList<>());
        dataFile.setMedicalRecords(new ArrayList<>());
        when(repo.getDataFile()).thenReturn(dataFile);
    }

    /**
     * Gets person by last name should return person info.
     */
    @Test
    void getPersonByLastName_shouldReturnPersonInfo() {
        // Personne
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setCity("City");
        person.setZip(12345);
        person.setPhone("123-456-7890");
        person.setEmail("john.doe@email.com");
        dataFile.getPersons().add(person);

        // Medical record
        MedicalRecord record = new MedicalRecord();
        record.setFirstName("John");
        record.setLastName("Doe");
        record.setBirthdate("01/01/2000");
        record.setMedications(List.of("med1:10mg"));
        record.setAllergies(List.of("peanuts"));
        dataFile.getMedicalRecords().add(record);

        // Exécution
        List<PersonInfoDTO> result = service.getPersonByLastName("Doe");

        // Vérifications
        assertThat(result).hasSize(1);
        PersonInfoDTO dto = result.get(0);
        assertThat(dto.getFirstName()).isEqualTo("John");
        assertThat(dto.getLastName()).isEqualTo("Doe");
        assertThat(dto.getAddress()).isEqualTo("123 Main St");
        assertThat(dto.getEmail()).isEqualTo("john.doe@email.com");
        assertThat(dto.getAllergies()).containsExactly("peanuts");
        assertThat(dto.getMedications()).containsExactly("med1:10mg");
        assertThat(dto.getAge()).isGreaterThan(0);
    }

    /**
     * Gets person by last name should return empty list when no match.
     */
    @Test
    void getPersonByLastName_shouldReturnEmptyList_whenNoMatch() {
        // Aucun ajout de personne ou medical record

        List<PersonInfoDTO> result = service.getPersonByLastName("Unknown");

        assertThat(result).isEmpty();
    }
}
