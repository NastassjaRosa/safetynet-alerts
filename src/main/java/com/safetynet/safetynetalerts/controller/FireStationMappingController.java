package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/firestation/mapping")
@RequiredArgsConstructor
public class FireStationMappingController {

    private final FireStationService service;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody FireStation fs) {
        return;
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody FireStation fs) { ... }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String address, @RequestParam int stationNumber) { ... }
}
