package com.safetynet.safetynetalerts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// une personne avec son nom telephone age médicaments et allergies


/**
 * The type Fire person dto.
 */
@Data
@AllArgsConstructor
public class FirePersonDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;


}
