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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * The type Community email service test.
 */
@ExtendWith(MockitoExtension.class)
class CommunityEmailServiceTest {

    @Mock
    private DataRepository repo;

    @InjectMocks
    private CommunityEmailService service;

    private DataFile dataFile;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        dataFile = new DataFile();
        dataFile.setPersons(new ArrayList<>());

        // Ajout de 2 personnes dans "Culver"
        Person p1 = new Person();
        p1.setFirstName("John");
        p1.setLastName("Boyd");
        p1.setCity("Culver");
        p1.setEmail("john@example.com");

        Person p2 = new Person();
        p2.setFirstName("Jacob");
        p2.setLastName("Boyd");
        p2.setCity("Culver");
        p2.setEmail("jacob@example.com");

        // 1 personne dans une autre ville
        Person p3 = new Person();
        p3.setFirstName("Toto");
        p3.setLastName("Tata");
        p3.setCity("Paris");
        p3.setEmail("toto@example.com");

        dataFile.getPersons().add(p1);
        dataFile.getPersons().add(p2);
        dataFile.getPersons().add(p3);

        when(repo.getDataFile()).thenReturn(dataFile);
    }

    /**
     * Gets emails by city should return distinct emails.
     */
    @Test
    void getEmailsByCity_shouldReturnDistinctEmails() {
        List<String> emails = service.getEmailsByCity("Culver");

        assertThat(emails).containsExactlyInAnyOrder("john@example.com", "jacob@example.com");
    }

    /**
     * Gets emails by city should return empty list when no match.
     */
    @Test
    void getEmailsByCity_shouldReturnEmptyListWhenNoMatch() {
        List<String> emails = service.getEmailsByCity("UnknownCity");

        assertThat(emails).isEmpty();
    }
}
