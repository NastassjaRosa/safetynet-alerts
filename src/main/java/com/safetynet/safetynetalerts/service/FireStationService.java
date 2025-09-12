package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.PersonCoverageDTO;
import com.safetynet.safetynetalerts.dto.StationCoverageDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;





/**
 * Règles métier liées aux données des casernes.
 */
@Slf4j
//Signale à Spring que cette classe est un bean contenant de la logique métier ; Spring la crée au démarrage (singleton)
@Service
//Annotation Lombok :Génère automatiquement un constructeur prenant tous les champs final ;évite d’écrire ce constructeur à la main.Spring utilisera ce constructeur pour injecter les dépendances.
@RequiredArgsConstructor

public class FireStationService {

    private final DataRepository repo;

    /**
     * Retourne toutes les casernes couvrant une adresse donnée.
     *
     * @param address adresse recherchée
     */
    public List<FireStation> findByAddress(String address) {
        return repo.getDataFile() // objet racine contenant toutes les listes
                .getFireStations() // List<FireStation> On prend toutes les casernes connues.
                .stream() // on passe en « mode pipeline » Les casernes sont toutes la . On n’a plus besoin de boucles ; le stream gère ""l’enchaînement"" des casernes.
                .filter(fs -> fs.getAddress().equals(address)) // pour chaque caserne, on lit l'addresse, si l'addresse correspond on garde donc on garde seulement l’adresse voulue
                .collect(Collectors.toList()); // toutes les caserne qui on passées le filtre sont rangées dans une nouvelles liste ""re-fabrique"" une List
    }
    /** Habitant·e·s + nombre d’adultes/enfants pour une caserne. */
    public StationCoverageDTO getCoverageByStation(int station) {

        // 1) adresses desservies par cette caserne
        Set<String> addresses = repo.getDataFile()
                .getFireStations()
                .stream()
                .filter(fs -> fs.getStation() == station)
                .map(FireStation::getAddress)
                .collect(Collectors.toSet());

        // 2) index "Prénom|Nom" -> MedicalRecord (pour récupérer birthdate)
        Map<String, MedicalRecord> recordByName = repo.getDataFile()
                .getMedicalRecords()
                .stream()
                .collect(Collectors.toMap(
                        r -> r.getFirstName() + "|" + r.getLastName(),
                        r -> r));

        // 3) liste des habitants concernés (on ne compte rien ici)
        List<PersonCoverageDTO> persons = repo.getDataFile()
                .getPersons()
                .stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .map(p -> new PersonCoverageDTO(
                        p.getFirstName(),
                        p.getLastName(),
                        p.getAddress(),
                        p.getPhone()))
                .toList();

        // 4) comptage adulte / enfant (après coup)
        DateTimeFormatter fmt  = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate         now  = LocalDate.now();

        long adultCount = persons.stream()
                .filter(dto -> {
                    MedicalRecord mr = recordByName.get(dto.getFirstName() + "|" + dto.getLastName());
                    if (mr == null) return false;
                    return Person.isAdult(mr.getBirthdate()); // Modifier pour appeler la méthode isAdult du model Person
                })
                .count();

        long childCount = persons.size() - adultCount;

        return new StationCoverageDTO(persons, (int) adultCount, (int) childCount);
    }





    //CRUD Mapping

    public void addMapping(FireStation fs) {
        repo.getDataFile().getFireStations().add(fs);
        log.debug("Mapping ajouté {}", fs);
    }

    public boolean updateMapping(FireStation fs) {
        return repo.getDataFile()
                .getFireStations()
                .stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(fs.getAddress()))
                .peek(f -> f.setStation(fs.getStation()))
                .findFirst()
                .isPresent();
    }

    public boolean deleteMapping(String address, int station) {
        return repo.getDataFile()
                .getFireStations()
                .removeIf(f -> f.getAddress().equalsIgnoreCase(address)
                        && f.getStation() == station);
    }


}