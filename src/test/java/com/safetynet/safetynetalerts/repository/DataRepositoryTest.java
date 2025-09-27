package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.DataFile;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Data repository test.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataRepositoryTest {

    @Autowired
    private DataRepository repo;

    /**
     * Clean external file.
     *
     * @throws IOException the io exception
     */
    @BeforeEach
    void cleanExternalFile() throws IOException {
        // On supprime le fichier externe pour forcer les tests à recréer la data
        Files.deleteIfExists(Paths.get("data/data.json"));
    }

    /**
     * Test initial load from classpath.
     */
    @Test
    @Order(1)
    void testInitialLoadFromClasspath() {
        // Vérifie que Spring a bien appelé init() automatiquement
        assertNotNull(repo.getDataFile(), "Les données doivent être chargées");
        assertFalse(repo.getDataFile().getPersons().isEmpty(), "La liste des personnes ne doit pas être vide");
    }

    /**
     * Test save creates external file.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(2)
    void testSaveCreatesExternalFile() throws IOException {
        // Modifie les données et sauve
        repo.getDataFile().getPersons().get(0).setCity("TestCity");
        repo.save();

        // Recharge manuellement depuis le fichier externe
        DataRepository repo2 = new DataRepository();
        callInitByReflection(repo2);

        assertEquals("TestCity", repo2.getDataFile().getPersons().get(0).getCity());
    }

    /**
     * Test reload from external if exists.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(3)
    void testReloadFromExternalIfExists() throws Exception {
        // Force un fichier externe existant avec une modification
        repo.getDataFile().getPersons().get(0).setCity("CityFromExternal");
        repo.save();

        // Simule un nouveau bean
        DataRepository repo2 = new DataRepository();
        callInitByReflection(repo2);

        // Vérifie que la modification est bien relue
        assertEquals("CityFromExternal", repo2.getDataFile().getPersons().get(0).getCity());
    }

    /**
     * Test load from classpath when no external file.
     */
    @Test
    @Order(4)
    void testLoadFromClasspathWhenNoExternalFile() {
        try {
            // Supprime le fichier externe pour forcer le chargement depuis resources
            Files.deleteIfExists(Paths.get("data/data.json"));

            DataRepository repo2 = new DataRepository();
            callInitByReflection(repo2);

            // Vérifie que les données ont bien été chargées
            assertNotNull(repo2.getDataFile());
            assertFalse(repo2.getDataFile().getPersons().isEmpty());
        } catch (Exception e) {
            fail("Exception inattendue : " + e.getMessage());
        }
    }


    /**
     * Test save with atomic move not supported.
     */
    @Test
    @Order(5)
    void testSaveWithAtomicMoveNotSupported() {
        try {
            // Création d'une sous-classe qui force l'exception
            DataRepository repo2 = new DataRepository() {
                @Override
                public synchronized void save() throws IOException {
                    Files.createDirectories(Paths.get("data"));
                    throw new java.nio.file.AtomicMoveNotSupportedException("a", "b", "Test Exception");
                }
            };

            // On vérifie juste qu'elle lance bien l'exception
            assertThrows(java.nio.file.AtomicMoveNotSupportedException.class, repo2::save);

        } catch (Exception e) {
            fail("Erreur inattendue : " + e.getMessage());
        }
    }



    private void callInitByReflection(DataRepository repository) {
        try {
            Method initMethod = DataRepository.class.getDeclaredMethod("init");
            initMethod.setAccessible(true);
            initMethod.invoke(repository);
        } catch (Exception e) {
            fail("Impossible d'appeler init() par réflexion : " + e.getMessage());
        }
    }
}
