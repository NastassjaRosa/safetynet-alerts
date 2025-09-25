package com.safetynet.safetynetalerts.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Age util test.
 */
class AgeUtilTest {

    /**
     * Gets age should return valid age.
     */
    @Test
    void getAge_shouldReturnValidAge() {
        int age = AgeUtil.getAge("01/01/2000");
        assertThat(age).isGreaterThan(0);
    }

    /**
     * Gets age should return minus one when null.
     */
    @Test
    void getAge_shouldReturnMinusOne_whenNull() {
        int age = AgeUtil.getAge(null);
        assertThat(age).isEqualTo(-1);
    }

    /**
     * Gets age should return minus one when blank.
     */
    @Test
    void getAge_shouldReturnMinusOne_whenBlank() {
        int age = AgeUtil.getAge("  ");
        assertThat(age).isEqualTo(-1);
    }

    /**
     * Gets age should return minus one when invalid date.
     */
    @Test
    void getAge_shouldReturnMinusOne_whenInvalidDate() {
        int age = AgeUtil.getAge("99/99/9999");
        assertThat(age).isEqualTo(-1);
    }

    /**
     * Is adult should return true when age above 18.
     */
    @Test
    void isAdult_shouldReturnTrue_whenAgeAbove18() {
        assertThat(AgeUtil.isAdult("01/01/2000")).isTrue();
    }

    /**
     * Is adult should return false when age below 18.
     */
    @Test
    void isAdult_shouldReturnFalse_whenAgeBelow18() {
        assertThat(AgeUtil.isAdult("01/01/2020")).isFalse();
    }
}
