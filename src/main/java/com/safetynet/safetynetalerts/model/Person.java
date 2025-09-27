package com.safetynet.safetynetalerts.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


/**
 * Représente une personne du fichier JSON.
 */
@Data

public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int zip;
    private String phone;
    private String email;


    /**
     * Calcule l'âge à partir de la date de naissance.
     *
     * @param birthdate the birthdate
     * @return the age
     */
    public int getAge(String birthdate) {
        if (birthdate == null || birthdate.isBlank()) return -1;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dob = LocalDate.parse(birthdate, fmt);
        return Period.between(dob, LocalDate.now()).getYears();
    }

}
