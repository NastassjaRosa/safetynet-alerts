package com.safetynet.safetynetalerts.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * The type Age util.
 */
public class AgeUtil {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Gets age.
     *
     * @param birthdate the birthdate
     * @return the age
     */
    public static int getAge(String birthdate) {
        if (birthdate == null || birthdate.isBlank()) return -1;
        try {
            LocalDate dob = LocalDate.parse(birthdate, FORMAT);
            return Period.between(dob, LocalDate.now()).getYears();
        } catch (DateTimeException e) {
            return -1;
        }
    }

    /**
     * Is adult boolean.
     *
     * @param birthdate the birthdate
     * @return the boolean
     */
    public static boolean isAdult(String birthdate) {
        return getAge(birthdate) > 18;
    }
}
