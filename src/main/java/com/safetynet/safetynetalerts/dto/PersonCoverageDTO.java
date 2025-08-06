package com.safetynet.safetynetalerts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Représente UNE personne dans la réponse de l’URL
 *  GET /firestation?stationNumber=X
 * Pourquoi un « DTO » ?  ➜  On ne veut pas exposer TOUT l’objet Person
 *  juste ce dont le front a besoin.
 */

// Lombok → génère getters, setters, equals, hashCode, toString
@Data
// Lombok → génère un constructeur avec TOUS les champs
@AllArgsConstructor
public class PersonCoverageDTO {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;

}
