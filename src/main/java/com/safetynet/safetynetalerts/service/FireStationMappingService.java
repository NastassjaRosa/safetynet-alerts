package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        log.debug("Mapping ajouté {}", fs);
    }

    /**
     * Update mapping boolean.
     *
     * @param fs the fs
     * @return the boolean
     */
    public boolean updateMapping(FireStation fs) {
        return repo.getDataFile()
                .getFireStations()
                .stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(fs.getAddress()))
                .peek(f -> f.setStation(fs.getStation()))
                .findFirst()
                .isPresent();
    }

    /**
     * Delete mapping boolean.
     *
     * @param address the address
     * @param station the station
     * @return the boolean
     */
    public boolean deleteMapping(String address, int station) {
        return repo.getDataFile()
                .getFireStations()
                .removeIf(f -> f.getAddress().equalsIgnoreCase(address)
                        && f.getStation() == station);
    }


}
