package com.safetynet.safetynetalerts.util;

import com.safetynet.safetynetalerts.dto.FirePersonDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

import java.util.List;

/**
 * The type Dto mapper util.
 */
public class DTOMapperUtil {

    /**
     * To fire person dto fire person dto.
     *
     * @param p  the p
     * @param mr the mr
     * @return the fire person dto
     */
    public static FirePersonDTO toFirePersonDTO(Person p, MedicalRecord mr) {
        int age = (mr != null && mr.getBirthdate() != null) ? AgeUtil.getAge(mr.getBirthdate()) : -1;
        return new FirePersonDTO(
                p.getFirstName(),
                p.getLastName(),
                p.getPhone(),
                age,
                (mr != null) ? mr.getMedications() : List.of(),
                (mr != null) ? mr.getAllergies() : List.of()
        );
    }
}
