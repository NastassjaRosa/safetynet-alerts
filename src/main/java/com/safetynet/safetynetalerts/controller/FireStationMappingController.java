package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationMappingService;
import com.safetynet.safetynetalerts.service.FireStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/firestation/mapping")
@RequiredArgsConstructor
public class FireStationMappingController {

    private final FireStationMappingService service;

    /**
     * POST – ajout d’un nouveau mapping adresse⇄caserne
     */
    @PostMapping
    public ResponseEntity<Void> add(@RequestBody FireStation fs) {
        service.addMapping(fs);
        log.info("Mapping created {}", fs);
        return ResponseEntity.status(201).build();   // HTTP 201 Created
    }

    /**
     * PUT – mise à jour du n° de caserne pour une adresse
     */
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody FireStation fs) {
        boolean updated = service.updateMapping(fs);
        if (updated) {
            log.info("Mapping updated {}", fs);
            return ResponseEntity.noContent().build();   // 204
        } else {
            log.warn("Mapping to update not found {}", fs);
            return ResponseEntity.notFound().build();    // 404
        }
        }
    /**
     * Delete – suppression d’un mapping adresse + station
     */
        @DeleteMapping
        public ResponseEntity<Void> delete (@RequestParam String address,
        @RequestParam int station){
            boolean removed = service.deleteMapping(address, station);
            if (removed) {
                log.info("Mapping deleted {} / {}", address, station);
                return ResponseEntity.noContent().build();
            }
            log.error("Mapping to delete not found {} / {}", address, station);
            return ResponseEntity.notFound().build();
        }
    }
