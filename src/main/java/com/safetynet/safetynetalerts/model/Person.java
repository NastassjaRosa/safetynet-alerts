package com.safetynet.safetynetalerts.model;

import lombok.Data;


/** Représente une personne du fichier JSON. */
@Data

public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int zip;
    private String phone;
    private String email;


}
