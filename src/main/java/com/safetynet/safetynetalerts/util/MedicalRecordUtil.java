package com.safetynet.safetynetalerts.util;

import com.safetynet.safetynetalerts.model.MedicalRecord;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MedicalRecordUtil {
    /** Crée un index Prénom Nom avec MedicalRecord pour tout le projet */
    public static Map<String, MedicalRecord> indexByName(List<MedicalRecord> records) {
        return records.stream()
                .collect(Collectors.toMap(
                        r -> r.getFirstName() + "|" + r.getLastName(),
                        Function.identity()
                ));
    }
}
