package com.safetynet.safetynetalerts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * The type Person info dto.
 */
@Data
@AllArgsConstructor
public class PersonInfoDTO {

    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private List<String> medications;
    private List<String> allergies;

}
