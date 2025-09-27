package com.safetynet.safetynetalerts.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.DataFile;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

/**
 * Charge le fichier data.json au démarrage
 * et expose les listes via des getters.
 */
@Slf4j
@Component //Permet a sprng de le detecter Spring remarque cette classe, la construit UNE fois (objet singleton)

public class DataRepository {

    private static final String CLASSPATH_PATH = "data/data.json";
    private static final Path EXTERNAL_DIR = Paths.get("data");           // dossier externe (racine projet)
    private static final Path EXTERNAL_PATH = EXTERNAL_DIR.resolve("data.json");

    //l’outil Jackson convertit JSON ↔ objets Java
    private final ObjectMapper mapper = new ObjectMapper();

    // stocke tout le contenu du JSON
    @Getter
    private DataFile dataFile;

    // Spring appelle cette méthode juste après avoir créé le bean exécute code d’initialisation
    @PostConstruct
    private void init() throws IOException {
        if (Files.exists(EXTERNAL_PATH) && Files.size(EXTERNAL_PATH) > 0) {
            // Lecture du fichier externe si déjà présent
            dataFile = mapper.readValue(EXTERNAL_PATH.toFile(), DataFile.class);
            logData("Données chargées depuis " + EXTERNAL_PATH.toAbsolutePath());
        } else {
            // Sinon on initialise depuis resources
            loadFromClasspathAndBootstrapExternal();
        }

    }

    /**
     * Charge les données initiales depuis resources/ et les copie dans data/data.json
     */
    private void loadFromClasspathAndBootstrapExternal() throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(CLASSPATH_PATH)) {
            if (is == null) {
                throw new IllegalStateException("Ressource introuvable: " + CLASSPATH_PATH);
            }
            dataFile = mapper.readValue(is, DataFile.class);
        }
        Files.createDirectories(EXTERNAL_DIR);
        save(); // création initiale
        logData("Copie initiale sauvegardée vers " + EXTERNAL_PATH.toAbsolutePath());
    }


    // Ecrit les données dans un fichier temporaire,
    // puis on le renomme en data.json uniquement quand tout est ok.
    // Evite d'avoir un fichier data.json incomplet si l'écriture plante.

    /**
     * Save.
     *
     * @throws IOException the io exception
     */
    public synchronized void save() throws IOException {
        Files.createDirectories(EXTERNAL_DIR);
        Path tmp = EXTERNAL_PATH.resolveSibling(EXTERNAL_PATH.getFileName() + ".tmp");
        mapper.writerWithDefaultPrettyPrinter().writeValue(tmp.toFile(), dataFile);
        try {
            Files.move(tmp, EXTERNAL_PATH,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException ex) {
            Files.move(tmp, EXTERNAL_PATH, StandardCopyOption.REPLACE_EXISTING);
        }
        logData("Données sauvegardées dans " + EXTERNAL_PATH.toAbsolutePath());
    }

    /**
     * Affiche le résumé avec les données
     */
    private void logData(String prefix) {
        log.info("{} : {} persons, {} firestations, {} medicalrecords",
                prefix,
                dataFile.getPersons().size(),
                dataFile.getFireStations().size(),
                dataFile.getMedicalRecords().size());
    }


}
