package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FirePersonDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.util.AgeUtil;
import com.safetynet.safetynetalerts.util.MedicalRecordUtil;
import com.safetynet.safetynetalerts.util.PersonFilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class FireService {
    private final DataRepository repo;

    public FireDTO getPersonsByAddress(String address) {
        log.debug("Recherche habitants et caserne pour {}", address);

        int stationNumber = repo.getDataFile().getFireStations().stream()
                .filter(fs -> fs.getAddress().equalsIgnoreCase(address))
                .map(FireStation::getStation)
                .findFirst()
                .orElse(-1);

        List<Person> persons = PersonFilterUtil.filterByAddress(
                repo.getDataFile().getPersons(), address
        );

        // Index Prénom Nom avec Dossier médical depuis util
        Map<String, MedicalRecord> recordByName =
                MedicalRecordUtil.indexByName(repo.getDataFile().getMedicalRecords());

        List<FirePersonDTO> personDTOs = persons.stream()
                .map(p -> {
                    MedicalRecord mr = recordByName.get(p.getFirstName() + "|" + p.getLastName());
                    int age = (mr != null && mr.getBirthdate() != null) ? AgeUtil.getAge(mr.getBirthdate()) : -1;
                    return new FirePersonDTO(
                            p.getFirstName(),
                            p.getLastName(),
                            p.getPhone(),
                            age,
                            (mr != null) ? mr.getMedications() : List.of(),
                            (mr != null) ? mr.getAllergies() : List.of()
                    );
                }).toList();

        log.info("Adresse {} → {} habitants trouvés", address, personDTOs.size());
        return new FireDTO(stationNumber, personDTOs);
    }
}