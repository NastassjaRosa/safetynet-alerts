package com.safetynet.safetynetalerts.controller;


import com.safetynet.safetynetalerts.dto.FloodDTO;
import com.safetynet.safetynetalerts.service.FloodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Flood controller.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/flood/stations")
public class FloodController {
    private final FloodService floodService;

    /**
     * Gets households by stations.
     *
     * @param stations the stations
     * @return the households by stations
     */
    @GetMapping
    public ResponseEntity<FloodDTO> getHouseholdsByStations(@RequestParam String stations) {
        log.debug("GET /flood/stations?stations={}", stations);

        List<Integer> stationList = Arrays.stream(stations.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        FloodDTO dto = floodService.getHouseholdsByStations(stationList);
        return ResponseEntity.ok(dto);
    }

}
