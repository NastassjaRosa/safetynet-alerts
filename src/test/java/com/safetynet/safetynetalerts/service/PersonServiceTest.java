package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DataFile;
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
 * The type Person service test.
 */
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private DataRepository repo;

    @InjectMocks
    private PersonService service;

    private DataFile dataFile;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        dataFile = new DataFile();
        dataFile.setPersons(new ArrayList<>());
        when(repo.getDataFile()).thenReturn(dataFile);
    }

    /**
     * Add person should add person.
     */
    @Test
    void addPerson_shouldAddPerson() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        service.addPerson(person);

        assertThat(dataFile.getPersons()).contains(person);
    }

    /**
     * Update person should return true when person exists.
     */
    @Test
    void updatePerson_shouldReturnTrue_whenPersonExists() {
        Person existing = new Person();
        existing.setFirstName("John");
        existing.setLastName("Doe");
        dataFile.getPersons().add(existing);

        Person updated = new Person();
        updated.setFirstName("John");
        updated.setLastName("Doe");
        updated.setAddress("123 Main St");
        updated.setCity("City");
        updated.setZip(12345);
        updated.setPhone("123-456");
        updated.setEmail("john@example.com");

        boolean result = service.updatePerson(updated);

        assertThat(result).isTrue();
        assertThat(existing.getAddress()).isEqualTo("123 Main St");
        assertThat(existing.getEmail()).isEqualTo("john@example.com");
    }

    /**
     * Update person should return false when person does not exist.
     */
    @Test
    void updatePerson_shouldReturnFalse_whenPersonDoesNotExist() {
        Person updated = new Person();
        updated.setFirstName("Jane");
        updated.setLastName("Smith");

        boolean result = service.updatePerson(updated);

        assertThat(result).isFalse();
    }

    /**
     * Delete person should return true when person exists.
     */
    @Test
    void deletePerson_shouldReturnTrue_whenPersonExists() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        dataFile.getPersons().add(person);

        boolean result = service.deletePerson("John", "Doe");

        assertThat(result).isTrue();
        assertThat(dataFile.getPersons()).isEmpty();
    }

    /**
     * Delete person should return false when person does not exist.
     */
    @Test
    void deletePerson_shouldReturnFalse_whenPersonDoesNotExist() {
        boolean result = service.deletePerson("Jane", "Smith");

        assertThat(result).isFalse();
    }
}
