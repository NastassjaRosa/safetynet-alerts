package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationMappingService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Fire station mapping controller test.
 */
@ExtendWith(MockitoExtension.class)
class FireStationMappingControllerTest {

    @Mock
    private FireStationMappingService service;

    @InjectMocks
    private FireStationMappingController controller;

    private MockMvc mvc;
    private ObjectMapper om;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        om = new ObjectMapper();
    }

    /**
     * Add should return 201.
     *
     * @throws Exception the exception
     */
    @Test
    void add_shouldReturn201() throws Exception {
        FireStation fs = new FireStation();
        fs.setAddress("29 15th St");
        fs.setStation(5);

        mvc.perform(post("/firestation/mapping")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(fs)))
                .andExpect(status().isCreated());

        verify(service).addMapping(any(FireStation.class));
    }

    /**
     * Update found should return 204.
     *
     * @throws Exception the exception
     */
    @Test
    void update_found_shouldReturn204() throws Exception {
        when(service.updateMapping(any(FireStation.class))).thenReturn(true);

        FireStation fs = new FireStation();
        fs.setAddress("1509 Culver St");
        fs.setStation(2);

        mvc.perform(put("/firestation/mapping")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(fs)))
                .andExpect(status().isNoContent());

        verify(service).updateMapping(any(FireStation.class));
    }

    /**
     * Update not found should return 404.
     *
     * @throws Exception the exception
     */
    @Test
    void update_notFound_shouldReturn404() throws Exception {
        when(service.updateMapping(any(FireStation.class))).thenReturn(false);

        FireStation fs = new FireStation();
        fs.setAddress("Unknown St");
        fs.setStation(9);

        mvc.perform(put("/firestation/mapping")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(fs)))
                .andExpect(status().isNotFound());
    }

    /**
     * Delete found should return 204.
     *
     * @throws Exception the exception
     */
    @Test
    void delete_found_shouldReturn204() throws Exception {
        when(service.deleteMapping("1509 Culver St", 3)).thenReturn(true);

        mvc.perform(delete("/firestation/mapping")
                        .param("address", "1509 Culver St")
                        .param("station", "3"))
                .andExpect(status().isNoContent());

        verify(service).deleteMapping("1509 Culver St", 3);
    }

    /**
     * Delete not found should return 404.
     *
     * @throws Exception the exception
     */
    @Test
    void delete_notFound_shouldReturn404() throws Exception {
        when(service.deleteMapping("nowhere", 1)).thenReturn(false);

        mvc.perform(delete("/firestation/mapping")
                        .param("address", "nowhere")
                        .param("station", "1"))
                .andExpect(status().isNotFound());
    }
}
