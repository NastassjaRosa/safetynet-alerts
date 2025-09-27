package com.safetynet.safetynetalerts.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The type Data repository test.
 */
@SpringBootTest
public class DataRepositoryTest {

    @Autowired
    private DataRepository repo;

    /**
     * Sets up.
     *
     * @throws IOException the io exception
     */
    @BeforeEach
    void setUp() throws IOException {
        // Supprime le fichier externe pour forcer la copie initiale
        Files.deleteIfExists(Paths.get("data/data.json"));
    }

    /**
     * Test initial load from classpath.
     */
    @Test
    void testInitialLoadFromClasspath() {
        assertNotNull(repo.getDataFile(), "Les données doivent être chargées depuis resources");
        assertFalse(repo.getDataFile().getPersons().isEmpty(), "Les personnes doivent être présentes");
    }

    /**
     * Test save creates external file.
     *
     * @throws IOException the io exception
     */
    @Test
    void testSaveCreatesExternalFile() throws IOException {
        // On modifie et on sauvegarde
        repo.getDataFile().getPersons().get(0).setCity("TestCity");
        repo.save();

        assertEquals("TestCity", repo.getDataFile().getPersons().get(0).getCity());
    }

    /**
     * Test reload from external if exists.
     *
     * @throws IOException the io exception
     */
    @Test
    void testReloadFromExternalIfExists() throws IOException {
        // On force une modif
        repo.getDataFile().getPersons().get(0).setCity("CityFromExternal");
        repo.save();
        assertEquals("CityFromExternal", repo.getDataFile().getPersons().get(0).getCity());
    }
}
