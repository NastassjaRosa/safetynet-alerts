package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.CommunityEmailService;
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
 * The type Community email controller test.
 */
@ExtendWith(MockitoExtension.class)
class CommunityEmailControllerTest {

    @Mock
    private CommunityEmailService service;

    @InjectMocks
    private CommunityEmailController controller;

    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /**
     * Gets emails should return list of emails.
     *
     * @throws Exception the exception
     */
    @Test
    void getEmails_shouldReturnListOfEmails() throws Exception {
        // GIVEN
        String city = "Culver";
        List<String> emails = List.of("john@example.com", "jane@example.com");
        when(service.getEmailsByCity(city)).thenReturn(emails);

        // WHEN + THEN
        mockMvc.perform(get("/communityEmail")
                        .param("city", city)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("john@example.com"))
                .andExpect(jsonPath("$[1]").value("jane@example.com"));
    }
}
