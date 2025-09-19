package com.safetynet.safetynetalerts.controller;


import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    /**
     * POST – ajout d'une nouvelle personne
     */
    @PostMapping
    public ResponseEntity<Void> add(@RequestBody Person person) {
        service.addPerson(person);
        log.info("Person created {}", person);
        return ResponseEntity.status(201).build(); // 201 Created
    }

    /**
     * PUT – mise à jour d'une personne existante
     */
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Person person) {
        boolean updated = service.updatePerson(person);
        if (updated) {
            log.info("Person updated {}", person);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            log.warn("Person to update not found {}", person);
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    /**
     * DELETE – suppression d'une personne par prénom + nom
     */
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String firstName,
                                       @RequestParam String lastName) {
        boolean removed = service.deletePerson(firstName, lastName);
        if (removed) {
            log.info("Person deleted {} {}", firstName, lastName);
            return ResponseEntity.noContent().build();
        }
        log.warn("Person to delete not found {} {}", firstName, lastName);
        return ResponseEntity.notFound().build();
    }

}
