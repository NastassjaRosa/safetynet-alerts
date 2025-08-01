package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Règles métier liées aux données des casernes.
 */

//Signale à Spring que cette classe est un bean contenant de la logique métier ; Spring la crée au démarrage (singleton)
@Service
//Annotation Lombok :Génère automatiquement un constructeur prenant tous les champs final ;évite d’écrire ce constructeur à la main.Spring utilisera ce constructeur pour injecter les dépendances.
@RequiredArgsConstructor

public class FireStationService {

    private final DataRepository dataRepository;

    /**
     * Retourne toutes les casernes couvrant une adresse donnée.
     *
     * @param address adresse recherchée
     */
    public List<FireStation> findByAddress(String address) {
        return dataRepository.getDataFile() // objet racine contenant toutes les listes
                .getFireStations() // List<FireStation> On prend toutes les casernes connues.
                .stream() // on passe en « mode pipeline » Les casernes sont toutes la . On n’a plus besoin de boucles ; le strem gère ""l’enchaînement"" des casernes.
                .filter(fs ->
                        fs.getAddress()
                                .equals(address)) // pour chaque caserne, on lit l'addresse, si l'addresse correspond on garde donc on garde seulement l’adresse voulue
                .collect(Collectors.toList()); // toutes les caserne qui on passées le filtre sont rangées dans une nouvelles liste ""re-fabrique"" une List
    }
    }
