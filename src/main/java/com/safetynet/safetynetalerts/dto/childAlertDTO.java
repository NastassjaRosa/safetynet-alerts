package com.safetynet.safetynetalerts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class childAlertDTO {
    private String firstName;
    private String lastName;
    private int age;
    private List<String> householdMenbers; //Liste des autres personnes de la maon mais pas l'enfant

}
