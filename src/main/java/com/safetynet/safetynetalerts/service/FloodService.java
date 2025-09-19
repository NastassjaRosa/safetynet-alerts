package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.FirePersonDTO;
import com.safetynet.safetynetalerts.dto.FloodDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.util.MedicalRecordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FloodService {
    private final DataRepository repo;
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
        Map<String, List<FirePersonDTO>> households = repo.getDataFile().getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .collect(Collectors.groupingBy(
                        Person::getAddress,
                        Collectors.mapping(p -> {
                            MedicalRecord mr = recordByName.get(p.getFirstName() + "|" + p.getLastName());
                            int age = (mr != null && mr.getBirthdate() != null) ? p.getAge(mr.getBirthdate()) : -1;
                            return new FirePersonDTO(
                                    p.getFirstName(),
                                    p.getLastName(),
                                    p.getPhone(),
                                    age,
                                    (mr != null) ? mr.getMedications() : List.of(),
                                    (mr != null) ? mr.getAllergies() : List.of()
                            );
                        }, Collectors.toList())
                ));

        log.info("Stations {} → {} foyers trouvés", stations, households.size());
        return new FloodDTO(households);
    }


}
