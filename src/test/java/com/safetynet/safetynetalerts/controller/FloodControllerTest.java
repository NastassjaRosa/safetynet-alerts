package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.FirePersonDTO;
import com.safetynet.safetynetalerts.dto.FloodDTO;
import com.safetynet.safetynetalerts.service.FloodService;
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
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Flood controller test.
 */
@ExtendWith(MockitoExtension.class)
class FloodControllerTest {

    @Mock
    private FloodService floodService;

    @InjectMocks
    private FloodController floodController;

    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(floodController).build();
    }

    /**
     * Gets households by stations should return flood dto.
     *
     * @throws Exception the exception
     */
    @Test
    void getHouseholdsByStations_shouldReturnFloodDTO() throws Exception {
        // GIVEN
        String stations = "1,2";
        FirePersonDTO person = new FirePersonDTO("John", "Doe", "123-456", 40, List.of("med1:100mg"), List.of("pollen"));
        FloodDTO dto = new FloodDTO(Map.of("1509 Culver St", List.of(person)));

        when(floodService.getHouseholdsByStations(List.of(1, 2))).thenReturn(dto);

        // WHEN + THEN
        mockMvc.perform(get("/flood/stations")
                        .param("stations", stations)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.households['1509 Culver St'][0].firstName").value("John"))
                .andExpect(jsonPath("$.households['1509 Culver St'][0].lastName").value("Doe"))
                .andExpect(jsonPath("$.households['1509 Culver St'][0].phone").value("123-456"))
                .andExpect(jsonPath("$.households['1509 Culver St'][0].age").value(40));
    }
}
