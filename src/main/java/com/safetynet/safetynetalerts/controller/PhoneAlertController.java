package com.safetynet.safetynetalerts.controller;


import com.safetynet.safetynetalerts.service.FireStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Phone alert controller.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/phoneAlert")
public class PhoneAlertController {


    private final FireStationService fireStationService;

    /**
     * Gets phones by station.
     *
     * @param firestation the firestation
     * @return the phones by station
     */
    @GetMapping(params = "firestation")
    public ResponseEntity<List<String>> getPhonesByStation(@RequestParam int firestation) {
        log.debug("GET /phoneAlert?firestation={}", firestation);

        List<String> phones = fireStationService.getPhonesByStation(firestation);

        log.info("Téléphones trouvés pour la station {} : {}", firestation, phones.size());
        return ResponseEntity.ok(phones);
    }


}
