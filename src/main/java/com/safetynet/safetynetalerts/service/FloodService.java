package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.FirePersonDTO;
import com.safetynet.safetynetalerts.dto.FloodDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.util.DTOMapperUtil;
import com.safetynet.safetynetalerts.util.MedicalRecordUtil;
import com.safetynet.safetynetalerts.util.PersonFilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Flood service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FloodService {
    private final DataRepository repo;

    /**
     * Gets households by stations.
     *
     * @param stations the stations
     * @return the households by stations
     */
    public FloodDTO getHouseholdsByStations(List<Integer> stations) {
        log.debug("Recherche des foyers pour les stations : {}", stations);

        // 1) Adresses couvertes par ces stations
        Set<String> addresses = repo.getDataFile().getFireStations().stream()
                .filter(fs -> stations.contains(fs.getStation()))
                .map(FireStation::getAddress)
                .collect(Collectors.toSet());

        // 2) Index Prénom Nom avec Dossier médical depuis util
        Map<String, MedicalRecord> recordByName =
                MedicalRecordUtil.indexByName(repo.getDataFile().getMedicalRecords());


        // 3) Habitants par adresse
        List<Person> personsAtStations = PersonFilterUtil.filterByStations(
                repo.getDataFile().getPersons(), addresses
        );

        Map<String, List<FirePersonDTO>> households = personsAtStations.stream()
                .collect(Collectors.groupingBy(
                        Person::getAddress,
                        Collectors.mapping(p -> DTOMapperUtil.toFirePersonDTO(p, recordByName.get(p.getFirstName() + "|" + p.getLastName())),
                                Collectors.toList())
                ));

        log.info("Stations {} → {} foyers trouvés", stations, households.size());
        return new FloodDTO(households);
    }


}
