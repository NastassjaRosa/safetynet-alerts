package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Person controller test.
 */
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private MockMvc mvc;
    private ObjectMapper om;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(personController).build();
        om = new ObjectMapper();
    }

    /**
     * Add person should return 201.
     *
     * @throws Exception the exception
     */
    @Test
    void addPerson_shouldReturn201() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Boyd");

        mvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(person)))
                .andExpect(status().isCreated());

        verify(personService).addPerson(any(Person.class));
    }

    /**
     * Update person found should return 204.
     *
     * @throws Exception the exception
     */
    @Test
    void updatePerson_found_shouldReturn204() throws Exception {
        when(personService.updatePerson(any(Person.class))).thenReturn(true);

        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Boyd");
        person.setAddress("New Address");

        mvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(person)))
                .andExpect(status().isNoContent());

        verify(personService).updatePerson(any(Person.class));
    }

    /**
     * Update person not found should return 404.
     *
     * @throws Exception the exception
     */
    @Test
    void updatePerson_notFound_shouldReturn404() throws Exception {
        when(personService.updatePerson(any(Person.class))).thenReturn(false);

        Person person = new Person();
        person.setFirstName("Unknown");
        person.setLastName("Person");

        mvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(person)))
                .andExpect(status().isNotFound());
    }

    /**
     * Delete person found should return 204.
     *
     * @throws Exception the exception
     */
    @Test
    void deletePerson_found_shouldReturn204() throws Exception {
        when(personService.deletePerson("John", "Boyd")).thenReturn(true);

        mvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Boyd"))
                .andExpect(status().isNoContent());

        verify(personService).deletePerson("John", "Boyd");
    }

    /**
     * Delete person not found should return 404.
     *
     * @throws Exception the exception
     */
    @Test
    void deletePerson_notFound_shouldReturn404() throws Exception {
        when(personService.deletePerson("Unknown", "Person")).thenReturn(false);

        mvc.perform(delete("/person")
                        .param("firstName", "Unknown")
                        .param("lastName", "Person"))
                .andExpect(status().isNotFound());
    }
}
