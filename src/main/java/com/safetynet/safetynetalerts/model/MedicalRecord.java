package com.safetynet.safetynetalerts.model;

import lombok.Data;

import java.util.List;

/**
 * The type Medical record.
 */
@Data

public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

}
