package com.safetynet.safetynetalerts.util;

import com.safetynet.safetynetalerts.model.Person;

import java.util.List;
import java.util.Set;

/**
 * The type Person filter util.
 */
public class PersonFilterUtil {
    /**
     * Filter by address list.
     *
     * @param persons the persons
     * @param address the address
     * @return the list
     */
    public static List<Person> filterByAddress(List<Person> persons, String address) {
        return persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .toList();
    }

    /**
     * Filter by stations list.
     *
     * @param persons   the persons
     * @param addresses the addresses
     * @return the list
     */
    public static List<Person> filterByStations(List<Person> persons, Set<String> addresses) {
        return persons.stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();
    }
}
