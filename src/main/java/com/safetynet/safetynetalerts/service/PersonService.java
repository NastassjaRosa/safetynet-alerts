package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.safetynet.safetynetalerts.util.SaveUtil.save;

/**
 * The type Person service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final DataRepository repo;




    /**
     * Add person.
     *
     * @param person the person
     */
    public void addPerson(Person person) {
        repo.getDataFile().getPersons().add(person);
        save(repo, "ajout de la personne");
        log.debug("Personne ajoutée : {} {}", person.getFirstName(), person.getLastName());
    }

    /**
     * Update person boolean.
     *
     * @param person the person
     * @return the boolean
     */
    public boolean updatePerson(Person person) {
        boolean updated = repo.getDataFile().getPersons()
                .stream()
                .filter(p ->p.getFirstName().equalsIgnoreCase(person.getFirstName())&&
                        p.getLastName().equalsIgnoreCase(person.getLastName()))
                .findFirst()
                .map(existing->{
                    existing.setAddress(person.getAddress());
                    existing.setCity(person.getCity());
                    existing.setZip(person.getZip());
                    existing.setPhone(person.getPhone());
                    existing.setEmail(person.getEmail());
                    return true;

                })
                .orElse(false);

        if (updated) {
            save(repo, "mise à jour de la personne");
            log.debug("Personne mise à jour : {} {}", person.getFirstName(), person.getLastName());        }
        return updated;

    }

    /**
     * Delete person boolean.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the boolean
     */
    public boolean deletePerson(String firstName, String lastName) {
        boolean removed = repo.getDataFile().getPersons()
                .removeIf(p -> p.getFirstName().equalsIgnoreCase(firstName) &&
                        p.getLastName().equalsIgnoreCase(lastName));
        if (removed) {
            save(repo, "suppression de la personne");
            log.debug("Personne supprimée : {} {}", firstName, lastName);
        }
        return removed;
    }



}
