package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.FloodDTO;
import com.safetynet.safetynetalerts.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FloodService {
    private final DataRepository repo;
    public FloodDTO getHouseholdsByStations(List<Integer> stations) {

    }


}
