package com.safetynet.safetynetalerts.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.DataFile;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Charge le fichier data.json au démarrage
 * et expose les listes via des getters.
 */
@Slf4j
@Component //Permet a sprng de le detecter Spring remarque cette classe, la construit UNE fois (objet singleton)

public class DataRepository {

    //l’outil Jackson convertit JSON ↔ objets Java
    private final ObjectMapper mapper = new ObjectMapper();

    // stocke tout le contenu du JSON
    @Getter
    private DataFile dataFile;

    // Spring appelle cette méthode juste après avoir créé le bean exécute code d’initialisation
    @PostConstruct
    private void init() throws IOException {
        String path = "/data/data.json";
        try (InputStream is = getClass().getResourceAsStream(path)) {
            dataFile = mapper.readValue(is, DataFile.class);
            log.info ("Json chargé : {} persons, {} firestations, {} medicalrecerds",
                    dataFile.getPersons().size(),
                    dataFile.getFireStations().size(),
                    dataFile.getMedicalRecords().size());
        }

    }


}
