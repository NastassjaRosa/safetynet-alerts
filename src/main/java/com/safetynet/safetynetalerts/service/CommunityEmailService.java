package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import com.safetynet.safetynetalerts.util.ContactUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Community email service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityEmailService {

    private final DataRepository repo;

    /**
     * Gets emails by city.
     *
     * @param city the city
     * @return the emails by city
     */
    public List<String> getEmailsByCity(String city) {
        log.debug("getEmailsByCity city: {}", city);

        List<Person> personsInCity = repo.getDataFile()
                .getPersons()
                .stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .toList();
        return ContactUtil.getDistinctEmails(personsInCity);
    }

}
