package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.util.AgeUtil;
import com.safetynet.safetynetalerts.util.MedicalRecordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonInfoService {
    private final DataRepository repo;

    public List<PersonInfoDTO> getPersonByLastName(String lastName) {

        //medical record accès
        Map<String, MedicalRecord> recordByName =
                MedicalRecordUtil.indexByName(repo.getDataFile().getMedicalRecords());


        return repo.getDataFile().getPersons()
                .stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .map(p -> {
                            MedicalRecord mr = recordByName.get(p.getFirstName() + "|" +p.getLastName());
                            int age = (mr != null) ? AgeUtil.getAge(mr.getBirthdate()) : -1;
                            return new PersonInfoDTO(
                                    p.getFirstName(),
                                    p.getLastName(),
                                    p.getAddress(),
                                    age,
                                    p.getEmail(),
                                    (mr != null) ? mr.getMedications() : List.of(),
                                    (mr != null) ? mr.getAllergies() : List.of()
                            );
                        })
                .collect(Collectors.toList());

    }


}
