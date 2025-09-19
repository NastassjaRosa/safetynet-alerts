package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.DataRepository;
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


        return new ArrayList<>();
    }


}
