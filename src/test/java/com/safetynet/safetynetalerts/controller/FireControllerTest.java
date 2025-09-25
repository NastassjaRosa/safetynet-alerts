package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.FirePersonDTO;
import com.safetynet.safetynetalerts.service.FireService;
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
 * The type Fire controller test.
 */
@ExtendWith(MockitoExtension.class)
class FireControllerTest {

    @Mock
    private FireService fireService;

    @InjectMocks
    private FireController fireController;

    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fireController).build();
    }

    /**
     * Gets persons by address should return fire dto.
     *
     * @throws Exception the exception
     */
    @Test
    void getPersonsByAddress_shouldReturnFireDTO() throws Exception {
        // GIVEN
        String address = "1509 Culver St";
        FirePersonDTO person = new FirePersonDTO("John", "Doe", "123-456", 35, List.of("med1:100mg"), List.of("pollen"));
        FireDTO fireDTO = new FireDTO(1, List.of(person));

        when(fireService.getPersonsByAddress(address)).thenReturn(fireDTO);

        // WHEN + THEN
        mockMvc.perform(get("/fire")
                        .param("address", address)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationNumber").value(1))
                .andExpect(jsonPath("$.persons[0].firstName").value("John"))
                .andExpect(jsonPath("$.persons[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.persons[0].phone").value("123-456"))
                .andExpect(jsonPath("$.persons[0].age").value(35));
    }
}
