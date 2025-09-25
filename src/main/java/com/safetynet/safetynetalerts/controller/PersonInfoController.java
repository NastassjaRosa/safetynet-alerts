package com.safetynet.safetynetalerts.controller;


import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.service.PersonInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Person info controller.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/personInfo")
public class PersonInfoController {
    private final PersonInfoService personInfoService;

    /**
     * Gets person info.
     *
     * @param lastName the last name
     * @return the person info
     */
    @GetMapping
    public ResponseEntity<List<PersonInfoDTO>> getPersonInfo(@RequestParam String lastName) {
        log.debug("GET /personInfo = {}", lastName);

        List<PersonInfoDTO> persons = personInfoService.getPersonByLastName(lastName);

        if (persons.isEmpty()) {
            log.info("Aucune personne trouvée pour lastName = {}", lastName);
            return ResponseEntity.notFound().build();
        }

        log.info("{} personne.s trouvée.spour lastName = {}", persons.size(), lastName);
        return ResponseEntity.ok(persons);
    }
}
