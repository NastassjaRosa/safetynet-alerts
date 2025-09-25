package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.FireStationService;
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
 * The type Phone alert controller test.
 */
@ExtendWith(MockitoExtension.class)
class PhoneAlertControllerTest {

    @Mock
    private FireStationService fireStationService;

    @InjectMocks
    private PhoneAlertController phoneAlertController;

    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(phoneAlertController).build();
    }

    /**
     * Gets phones by station should return list of phones.
     *
     * @throws Exception the exception
     */
    @Test
    void getPhonesByStation_shouldReturnListOfPhones() throws Exception {
        // GIVEN
        int stationNumber = 1;
        when(fireStationService.getPhonesByStation(stationNumber))
                .thenReturn(List.of("123-456-7890", "987-654-3210"));

        // WHEN + THEN
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", String.valueOf(stationNumber))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("123-456-7890"))
                .andExpect(jsonPath("$[1]").value("987-654-3210"));
    }

    /**
     * Gets phones by station should return empty list when no phones.
     *
     * @throws Exception the exception
     */
    @Test
    void getPhonesByStation_shouldReturnEmptyList_whenNoPhones() throws Exception {
        // GIVEN
        int stationNumber = 99;
        when(fireStationService.getPhonesByStation(stationNumber)).thenReturn(List.of());

        // WHEN + THEN
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", String.valueOf(stationNumber))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
