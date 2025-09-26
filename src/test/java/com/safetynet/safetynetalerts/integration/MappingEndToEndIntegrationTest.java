package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Mapping end to end integration test.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MappingEndToEndIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private DataRepository repo;

    /**
     * Reset data.
     */
    @BeforeEach
    void resetData() {
    }

    /**
     * Post put delete mapping flow.
     */
    @Test
    void post_put_delete_mapping_flow() {
        FireStation newFs = new FireStation();
        newFs.setAddress("Integration St");
        newFs.setStation(9);

        // POST
        ResponseEntity<Void> postResp = rest.postForEntity("/firestation/mapping", newFs, Void.class);
        assertThat(postResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // verify via GET address
        ResponseEntity<FireStation[]> getResp = rest.getForEntity("/firestation?address=Integration St", FireStation[].class);
        assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResp.getBody()).isNotNull();
        assertThat(getResp.getBody()).hasSize(1);
        assertThat(getResp.getBody()[0].getStation()).isEqualTo(9);

        // PUT update station -> 5
        newFs.setStation(5);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FireStation> putEntity = new HttpEntity<>(newFs, headers);
        ResponseEntity<Void> putResp = rest.exchange("/firestation/mapping", HttpMethod.PUT, putEntity, Void.class);
        assertThat(putResp.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // verify update
        getResp = rest.getForEntity("/firestation?address=Integration St", FireStation[].class);
        assertThat(getResp.getBody()[0].getStation()).isEqualTo(5);

        // DELETE mapping
        ResponseEntity<Void> delResp = rest.exchange("/firestation/mapping?address=Integration St&station=5",
                HttpMethod.DELETE, null, Void.class);
        assertThat(delResp.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // verify removed
        getResp = rest.getForEntity("/firestation?address=Integration St", FireStation[].class);
        assertThat(getResp.getBody()).isEmpty();
    }
}
