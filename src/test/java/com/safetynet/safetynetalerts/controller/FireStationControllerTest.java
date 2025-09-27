package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.PersonCoverageDTO;
import com.safetynet.safetynetalerts.dto.StationCoverageDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Fire station controller test.
 */
@ExtendWith(MockitoExtension.class)
class FireStationControllerTest {

    @Mock
    private FireStationService service;

    @InjectMocks
    private FireStationController controller;

    private MockMvc mvc;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /**
     * Gets by address should return list.
     *
     * @throws Exception the exception
     */
    @Test
    void getByAddress_shouldReturnList() throws Exception {
        FireStation fs = new FireStation();
        fs.setAddress("1509 Culver St");
        fs.setStation(3);

        when(service.findByAddress("1509 Culver St")).thenReturn(List.of(fs));

        mvc.perform(get("/firestation").param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[0].station", is(3)));
    }

    /**
     * Gets coverage by station should return dto.
     *
     * @throws Exception the exception
     */
    @Test
    void getCoverageByStation_shouldReturnDTO() throws Exception {
        var persons = List.of(
                new PersonCoverageDTO("Peter", "Duncan", "644 Gershwin Cir", "841-874-6512"),
                new PersonCoverageDTO("Reginold", "Walker", "908 73rd St", "841-874-8547")
        );
        StationCoverageDTO dto = new StationCoverageDTO(persons, 2, 0);

        when(service.getCoverageByStation(1)).thenReturn(dto);

        mvc.perform(get("/firestation").param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persons", hasSize(2)))
                .andExpect(jsonPath("$.adultCount", is(2)))
                .andExpect(jsonPath("$.childCount", is(0)))
                .andExpect(jsonPath("$.persons[0].firstName", is("Peter")));
    }
}
