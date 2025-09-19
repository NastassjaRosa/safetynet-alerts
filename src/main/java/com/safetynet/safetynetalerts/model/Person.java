package com.safetynet.safetynetalerts.model;

import lombok.Data;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


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

// Methode qui determine si adulte > 18 ou enfant <= 18 isAdult
    /**
     * Détermine si cette personne est adulte (> 18 ans)
     *
     * @param birthdate la date de naissance (format "MM/dd/yyyy")
     * @return true si adulte (>18), false sinon
     */
//    public static boolean isAdult(String birthdate) {
//        if (birthdate == null || birthdate.isBlank()) return false;
//
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        int age;
//        try {
//            LocalDate dob = LocalDate.parse(birthdate, fmt);
//            age = Period.between(dob, LocalDate.now()).getYears();
//        } catch (DateTimeException e) {
//            return false;
//        }
//        return age > 18;
//    }

    //calcul l'age utiliser pour childAlert pour avoir l'age de l'enfant dans la réponse
    /**
     * Calcule l'âge à partir de la date de naissance.
     */
    public int getAge(String birthdate) {
        if (birthdate == null || birthdate.isBlank()) return -1;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dob = LocalDate.parse(birthdate, fmt);
        return Period.between(dob, LocalDate.now()).getYears();
    }

}
