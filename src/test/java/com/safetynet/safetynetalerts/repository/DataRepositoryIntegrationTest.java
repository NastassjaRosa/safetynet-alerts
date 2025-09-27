package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.DataFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Data repository integration test.
 */
@SpringBootTest
class DataRepositoryIntegrationTest {

    @Autowired
    private DataRepository repo;

    /**
     * Data is loaded from json.
     */
    @Test
    void dataIsLoadedFromJson() {
        DataFile df = repo.getDataFile();
        assertThat(df).isNotNull();
        assertThat(df.getPersons()).isNotNull();
        assertThat(df.getFireStations()).isNotNull();
        assertThat(df.getMedicalRecords()).isNotNull();

        // valeurs observées dans ton data.json initial (logs précédents)
        assertThat(df.getPersons().size()).isEqualTo(23);
        assertThat(df.getFireStations().size()).isEqualTo(13);
        assertThat(df.getMedicalRecords().size()).isEqualTo(23);
    }
}
