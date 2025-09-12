package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;



@Slf4j
@Service
@RequiredArgsConstructor

public class ChildAlertService {

    private final DataRepository repo;

    public List<ChildAlertDTO> getChildrenByAddress(String address) {

        //recuperer les personnes de dataRepository et filter personnes a l'adresse
        List<Person> personsAtAddress = repo.getDataFile().getPersons().stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());

        //Va chercher le  Medical record de chaque personne pour avoir la date de naissance
        Map<String, MedicalRecord> recordByName = repo.getDataFile().getMedicalRecords().stream()
                .collect(Collectors.toMap(
                        mr -> mr.getFirstName() + "|" + mr.getLastName(),
                        Function.identity()
                ));
//determiner enfant puis l'exclure
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        List<ChildAlertDTO> children = new ArrayList<>();

        for (Person p : personsAtAddress) {
            MedicalRecord mr = recordByName.get(p.getFirstName() + "|" + p.getLastName());
            if (mr == null || mr.getBirthdate() == null || mr.getBirthdate().isBlank()) continue;

            if (!p.isAdult(mr.getBirthdate())) {
                int age = p.getAge(mr.getBirthdate());

                // Enlève les enfants de la liste
                List<String> householdMembers = personsAtAddress.stream()
                        .filter(other -> !(other.getFirstName().equals(p.getFirstName()) && other.getLastName().equals(p.getLastName())))
                        .map(other -> other.getFirstName() + " " + other.getLastName())
                        .collect(Collectors.toList());

                children.add(new ChildAlertDTO(p.getFirstName(), p.getLastName(), age, householdMembers));
            }
        }



        return children;
    }
}
