package com.safetynet.safetynetalerts.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Person test.
 */
class PersonTest {

    /**
     * Gets age should return correct age when valid birthdate.
     */
    @Test
    void getAge_shouldReturnCorrectAge_whenValidBirthdate() {
        Person person = new Person();
        int age = person.getAge("01/01/2000"); // date fixe
        assertTrue(age > 0, "L'âge doit être positif pour une date passée");
    }

    /**
     * Gets age should return minus one when birthdate is null.
     */
    @Test
    void getAge_shouldReturnMinusOne_whenBirthdateIsNull() {
        Person person = new Person();
        int age = person.getAge(null);
        assertEquals(-1, age, "Si la date est null, l'âge doit être -1");
    }

    /**
     * Gets age should return minus one when birthdate is blank.
     */
    @Test
    void getAge_shouldReturnMinusOne_whenBirthdateIsBlank() {
        Person person = new Person();
        int age = person.getAge("   ");
        assertEquals(-1, age, "Si la date est vide, l'âge doit être -1");
    }
}
