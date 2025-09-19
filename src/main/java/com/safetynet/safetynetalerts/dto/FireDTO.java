package com.safetynet.safetynetalerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;



// numéro de la caserne plus la liste de FirePersonneDTO

@Data
@AllArgsConstructor
public class FireDTO {

    private int stationNumber;
    private List<FirePersonDTO> persons;
}
