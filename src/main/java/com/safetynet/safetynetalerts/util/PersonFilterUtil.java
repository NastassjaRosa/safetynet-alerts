package com.safetynet.safetynetalerts.util;

import com.safetynet.safetynetalerts.model.Person;

import java.util.List;
import java.util.Set;

public class PersonFilterUtil {
    public static List<Person> filterByAddress(List<Person> persons, String address) {
        return persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .toList();
    }

    public static List<Person> filterByStations(List<Person> persons, Set<String> addresses) {
        return persons.stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();
    }
}
