package com.safetynet.safetynetalerts.controller;


import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.service.FireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/fire")
public class FireController {

    private final FireService fireService;

    @GetMapping
    public ResponseEntity<FireDTO> getPersonsByAddress(@RequestParam String address) {
        log.debug("GET /fire?address={}", address);
        FireDTO dto = fireService.getPersonsByAddress(address);
        return ResponseEntity.ok(dto);
    }

}
