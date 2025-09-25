package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.service.ChildAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Child alert controller test.
 */
@ExtendWith(MockitoExtension.class)
class ChildAlertControllerTest {

    @Mock
    private ChildAlertService childAlertService;

    @InjectMocks
    private ChildAlertController childAlertController;

    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(childAlertController).build();
    }

    /**
     * Gets children should return children list.
     *
     * @throws Exception the exception
     */
    @Test
    void getChildren_shouldReturnChildrenList() throws Exception {
        // GIVEN
        String address = "1509 Culver St";
        List<ChildAlertDTO> children = List.of(
                new ChildAlertDTO("John", "Doe", 10, List.of("Jane Doe"))
        );
        when(childAlertService.getChildrenByAddress(address)).thenReturn(children);

        // WHEN + THEN
        mockMvc.perform(get("/childAlert")
                        .param("address", address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].age").value(10));
    }
}
