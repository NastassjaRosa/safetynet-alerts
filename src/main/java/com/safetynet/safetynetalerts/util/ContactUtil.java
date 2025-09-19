package com.safetynet.safetynetalerts.util;

import com.safetynet.safetynetalerts.model.Person;

import java.util.List;
import java.util.Objects;

public class ContactUtil {

    public static List<String> getDistinctEmails(List<Person> persons) {
        return persons.stream()
                .map(Person::getEmail)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    public static List<String> getDistinctPhones(List<Person> persons) {
        return persons.stream()
                .map(Person::getPhone)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

}
