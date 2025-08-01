package com.safetynet.safetynetalerts.controller;


import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints REST relatifs aux casernes Fire Stations.
 * controller, recois la requette et envois la réponse
 */

//Combine @Controller (déclare une classe MVC) + @ResponseBody (tout ce qu'il je retourne est converti en JSON
@RestController
//Préfixe d’URL : tous les chemins de ce contrôleur commenceront par /firestation
@RequestMapping ("/firestation")
//Génère un constructeur prenant en paramètre chaque champ final. Ici, ça crée public FireStationController(FireStationService service) automatiquement
@RequiredArgsConstructor

public class FireStationController {

    private final FireStationService fireStationService;
    /**
     * GET /firestation?address=...
     * @return la/les casernes couvrant l’adresse.
     */

    @GetMapping
    public ResponseEntity <List<FireStation>> getByAdress(@RequestParam String address) {
        List<FireStation> result = fireStationService.findByAddress(address);
        return ResponseEntity.ok(result);
    }
}
