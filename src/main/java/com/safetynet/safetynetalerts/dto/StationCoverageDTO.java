package com.safetynet.safetynetalerts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
/**
 * DTO « enveloppe » renvoyé par l’endpoint
 *  GET /firestation?stationNumber=X
 *
 * Contient :
 *  • la liste formatée des habitants        (persons)
 *  • le nombre d’adultes (>18 ans)          (adultCount)
 *  • le nombre d’enfants (≤18 ans)          (childCount)
 */
@Data
@AllArgsConstructor
public class StationCoverageDTO {
    private List<PersonCoverageDTO> persons; // ON récupère la liste crée par le DTO PersonCoverageDTO
    private int adultCount;
    private int childCount;

}
