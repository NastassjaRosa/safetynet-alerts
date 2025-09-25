package com.safetynet.safetynetalerts.util;

import com.safetynet.safetynetalerts.model.Person;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Contact util test.
 */
class ContactUtilTest {

    /**
     * Gets distinct emails should return unique emails.
     */
    @Test
    void getDistinctEmails_shouldReturnUniqueEmails() {
        // GIVEN
        Person p1 = new Person();
        p1.setEmail("test1@mail.com");
        Person p2 = new Person();
        p2.setEmail("test2@mail.com");
        Person p3 = new Person();
        p3.setEmail("test1@mail.com"); // duplicate

        // WHEN
        List<String> emails = ContactUtil.getDistinctEmails(List.of(p1, p2, p3));

        // THEN
        assertThat(emails).containsExactlyInAnyOrder("test1@mail.com", "test2@mail.com");
    }

    /**
     * Gets distinct emails should return empty list when no emails.
     */
    @Test
    void getDistinctEmails_shouldReturnEmptyListWhenNoEmails() {
        // GIVEN
        Person p1 = new Person(); // no email

        // WHEN
        List<String> emails = ContactUtil.getDistinctEmails(List.of(p1));

        // THEN
        assertThat(emails).isEmpty();
    }

    /**
     * Gets distinct phones should return unique phones.
     */
    @Test
    void getDistinctPhones_shouldReturnUniquePhones() {
        // GIVEN
        Person p1 = new Person();
        p1.setPhone("111-222");
        Person p2 = new Person();
        p2.setPhone("333-444");
        Person p3 = new Person();
        p3.setPhone("111-222"); // duplicate

        // WHEN
        List<String> phones = ContactUtil.getDistinctPhones(List.of(p1, p2, p3));

        // THEN
        assertThat(phones).containsExactlyInAnyOrder("111-222", "333-444");
    }

    /**
     * Gets distinct phones should return empty list when no phones.
     */
    @Test
    void getDistinctPhones_shouldReturnEmptyListWhenNoPhones() {
        // GIVEN
        Person p1 = new Person(); // no phone

        // WHEN
        List<String> phones = ContactUtil.getDistinctPhones(List.of(p1));

        // THEN
        assertThat(phones).isEmpty();
    }
}

