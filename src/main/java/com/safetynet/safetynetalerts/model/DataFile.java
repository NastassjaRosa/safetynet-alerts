package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DataFile {

    private List<Person> persons;


    /**
     * L’annotation @JsonProperty("medicalrecords") indique à Jackson
     * d’associer la clef JSON "medicalrecords" au champ Java
     * medicalRecords
     * Lors de la désérialisation, Jackson lira la valeur de
     * "medicalrecords" du fichier JSON pour remplir ce champ.
     * Lors de la **sérialisation**, Jackson écrira ce champ sous la même
     * clef "medicalrecords" dans le JSON généré.
     * Ainsi, on peut conserver la convention camelCase côté Java tout en
     * restant compatible avec le nom exact présent dans data.json.
     */

    @JsonProperty("firestations")
    private List<FireStation> fireStations;

    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecords;


}
