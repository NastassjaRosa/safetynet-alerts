package com.safetynet.safetynetalerts.model;

import lombok.Data;

import java.util.List;

@Data

public class MedicalRecord {

    private String firstname;
    private String lastname;
    private String birthdate;
    private List<String> medication;
    private List<String> allergies;

}
