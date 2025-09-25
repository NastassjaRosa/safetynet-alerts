package com.safetynet.safetynetalerts.util;

import com.safetynet.safetynetalerts.model.MedicalRecord;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Medical record util.
 */
public class MedicalRecordUtil {
    /**
     * Crée un index Prénom Nom avec MedicalRecord pour tout le projet  @param records the records
     *
     * @param records the records
     * @return the map
     */
    public static Map<String, MedicalRecord> indexByName(List<MedicalRecord> records) {
        return records.stream()
                .collect(Collectors.toMap(
                        r -> r.getFirstName() + "|" + r.getLastName(),
                        Function.identity()
                ));
    }
}
