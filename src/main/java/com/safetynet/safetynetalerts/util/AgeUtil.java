package com.safetynet.safetynetalerts.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class AgeUtil {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static int getAge(String birthdate) {
        if (birthdate == null || birthdate.isBlank()) return -1;
        try {
            LocalDate dob = LocalDate.parse(birthdate, FORMAT);
            return Period.between(dob, LocalDate.now()).getYears();
        } catch (DateTimeException e) {
            return -1;
        }
    }

    public static boolean isAdult(String birthdate) {
        return getAge(birthdate) > 18;
    }
}
