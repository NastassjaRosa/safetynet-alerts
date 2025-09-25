package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
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
 * The type Medical record controller test.
 */
@ExtendWith(MockitoExtension.class)
class MedicalRecordControllerTest {

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    private MockMvc mvc;
    private ObjectMapper om;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(medicalRecordController).build();
        om = new ObjectMapper();
    }

    /**
     * Add medical record should return 201.
     *
     * @throws Exception the exception
     */
    @Test
    void addMedicalRecord_shouldReturn201() throws Exception {
        MedicalRecord record = new MedicalRecord();
        record.setFirstName("John");
        record.setLastName("Boyd");

        mvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(record)))
                .andExpect(status().isCreated());

        verify(medicalRecordService).addMedicalRecord(any(MedicalRecord.class));
    }

    /**
     * Update medical record found should return 204.
     *
     * @throws Exception the exception
     */
    @Test
    void updateMedicalRecord_found_shouldReturn204() throws Exception {
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class))).thenReturn(true);

        MedicalRecord record = new MedicalRecord();
        record.setFirstName("John");
        record.setLastName("Boyd");
        record.setBirthdate("01/01/2000");

        mvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(record)))
                .andExpect(status().isNoContent());

        verify(medicalRecordService).updateMedicalRecord(any(MedicalRecord.class));
    }

    /**
     * Update medical record not found should return 404.
     *
     * @throws Exception the exception
     */
    @Test
    void updateMedicalRecord_notFound_shouldReturn404() throws Exception {
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class))).thenReturn(false);

        MedicalRecord record = new MedicalRecord();
        record.setFirstName("Unknown");
        record.setLastName("Person");

        mvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(record)))
                .andExpect(status().isNotFound());
    }

    /**
     * Delete medical record found should return 204.
     *
     * @throws Exception the exception
     */
    @Test
    void deleteMedicalRecord_found_shouldReturn204() throws Exception {
        when(medicalRecordService.deleteMedicalRecord("John", "Boyd")).thenReturn(true);

        mvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Boyd"))
                .andExpect(status().isNoContent());

        verify(medicalRecordService).deleteMedicalRecord("John", "Boyd");
    }

    /**
     * Delete medical record not found should return 404.
     *
     * @throws Exception the exception
     */
    @Test
    void deleteMedicalRecord_notFound_shouldReturn404() throws Exception {
        when(medicalRecordService.deleteMedicalRecord("Unknown", "Person")).thenReturn(false);

        mvc.perform(delete("/medicalRecord")
                        .param("firstName", "Unknown")
                        .param("lastName", "Person"))
                .andExpect(status().isNotFound());
    }
}
