package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final DataRepository repo;

    public void addPerson(Person person) {
        repo.getDataFile().getPersons().add(person);
        log.info("Person added: {} {}", person.getFirstName(), person.getLastName());
    }

    public boolean updatePerson(Person person) {
        return repo.getDataFile().getPersons()
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
    }

    public boolean deletePerson(String firstName, String lastName) {
        boolean removed = repo.getDataFile().getPersons()
                .removeIf(p -> p.getFirstName().equalsIgnoreCase(firstName) &&
                        p.getLastName().equalsIgnoreCase(lastName));
        if (removed) {
            log.info("Person deleted: {} {}", firstName, lastName);
        }
        return removed;
    }



}
