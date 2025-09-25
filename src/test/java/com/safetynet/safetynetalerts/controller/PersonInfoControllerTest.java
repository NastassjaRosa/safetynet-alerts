package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.service.PersonInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Person info controller test.
 */
@ExtendWith(MockitoExtension.class)
class PersonInfoControllerTest {

    @Mock
    private PersonInfoService personInfoService;

    @InjectMocks
    private PersonInfoController personInfoController;

    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personInfoController).build();
    }

    /**
     * Gets person info should return person list.
     *
     * @throws Exception the exception
     */
    @Test
    void getPersonInfo_shouldReturnPersonList() throws Exception {
        // GIVEN
        String lastName = "Boyd";
        PersonInfoDTO person = new PersonInfoDTO(
                "John",
                "Boyd",
                "1509 Culver St",
                38,
                "john@example.com",
                List.of("med1:100mg"),
                List.of("pollen")
        );
        when(personInfoService.getPersonByLastName(lastName)).thenReturn(List.of(person));

        // WHEN + THEN
        mockMvc.perform(get("/personInfo")
                        .param("lastName", lastName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Boyd"))
                .andExpect(jsonPath("$[0].address").value("1509 Culver St"))
                .andExpect(jsonPath("$[0].age").value(38))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    /**
     * Gets person info should return 404 when no person found.
     *
     * @throws Exception the exception
     */
    @Test
    void getPersonInfo_shouldReturn404_whenNoPersonFound() throws Exception {
        // GIVEN
        String lastName = "Unknown";
        when(personInfoService.getPersonByLastName(lastName)).thenReturn(List.of());

        // WHEN + THEN
        mockMvc.perform(get("/personInfo")
                        .param("lastName", lastName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
