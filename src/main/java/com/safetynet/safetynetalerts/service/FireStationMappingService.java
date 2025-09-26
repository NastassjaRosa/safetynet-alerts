package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.safetynet.safetynetalerts.util.SaveUtil.save;

/**
 * Règles métier liées aux données des casernes.
 */
@Slf4j
//Signale à Spring que cette classe est un bean contenant de la logique métier ; Spring la crée au démarrage (singleton)
@Service
//Annotation Lombok :Génère automatiquement un constructeur prenant tous les champs final ;évite d’écrire ce constructeur à la main.Spring utilisera ce constructeur pour injecter les dépendances.
@RequiredArgsConstructor
public class FireStationMappingService {
    private final DataRepository repo;





//CRUD Mapping

    /**
     * Add mapping.
     *
     * @param fs the fs
     */
    public void addMapping(FireStation fs) {
        repo.getDataFile().getFireStations().add(fs);
        save(repo, "ajout de la caserne");
        log.debug("Mapping ajouté {}", fs);
    }

    /**
     * Update mapping boolean.
     *
     * @param fs the fs
     * @return the boolean
     */
    public boolean updateMapping(FireStation fs) {
        boolean updated = repo.getDataFile()
                .getFireStations()
                .stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(fs.getAddress()))
                .peek(f -> f.setStation(fs.getStation()))
                .findFirst()
                .isPresent();

        if (updated) {
            save(repo, "mise à jour de la caserne");
            log.debug("Mapping mis à jour : {}", fs);
        }
        return updated;

    }

    /**
     * Delete mapping boolean.
     *
     * @param address the address
     * @param station the station
     * @return the boolean
     */
    public boolean deleteMapping(String address, int station) {
        boolean removed = repo.getDataFile()
                .getFireStations()
                .removeIf(f -> f.getAddress().equalsIgnoreCase(address)
                        && f.getStation() == station);

        if (removed) {
            save(repo, "suppression de la caserne");
            log.debug("Mapping supprimé : {} - station {}", address, station);
        }
        return removed;


    }


}
