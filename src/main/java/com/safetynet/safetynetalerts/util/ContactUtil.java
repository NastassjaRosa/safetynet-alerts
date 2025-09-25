package com.safetynet.safetynetalerts.util;

import com.safetynet.safetynetalerts.model.Person;

import java.util.List;
import java.util.Objects;

/**
 * The type Contact util.
 */
public class ContactUtil {

    /**
     * Gets distinct emails.
     *
     * @param persons the persons
     * @return the distinct emails
     */
    public static List<String> getDistinctEmails(List<Person> persons) {
        return persons.stream()
                .map(Person::getEmail)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    /**
     * Gets distinct phones.
     *
     * @param persons the persons
     * @return the distinct phones
     */
    public static List<String> getDistinctPhones(List<Person> persons) {
        return persons.stream()
                .map(Person::getPhone)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

}
