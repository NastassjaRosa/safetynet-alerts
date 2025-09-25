package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.StationCoverageDTO;
import com.safetynet.safetynetalerts.model.DataFile;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {

    @Mock
    private DataRepository repo;

    @InjectMocks
    private FireStationService service;

    private DataFile dataFile;

    @BeforeEach
    void setUp() {
        dataFile = new DataFile();
        when(repo.getDataFile()).thenReturn(dataFile);
    }

    @Test
    void getCoverageByStation_shouldReturnEmpty_whenNoStations() {
        dataFile.setFireStations(List.of());
        dataFile.setPersons(List.of());
        dataFile.setMedicalRecords(List.of());

        StationCoverageDTO dto = service.getCoverageByStation(1);

        assertThat(dto.getPersons()).isEmpty();
        assertThat(dto.getAdultCount()).isZero();
        assertThat(dto.getChildCount()).isZero();
    }

    @Test
    void getPhonesByStation_shouldReturnPhones_whenStationExists() {
        FireStation fs = new FireStation(); fs.setAddress("Addr1"); fs.setStation(1);
        Person p1 = new Person(); p1.setFirstName("A"); p1.setLastName("One"); p1.setAddress("Addr1"); p1.setPhone("111");

        dataFile.setFireStations(List.of(fs));
        dataFile.setPersons(List.of(p1));
        dataFile.setMedicalRecords(List.of());

        List<String> phones = service.getPhonesByStation(1);

        assertThat(phones).containsExactly("111");
    }

    @Test
    void getPhonesByStation_shouldReturnEmpty_whenNoPersonsAtStation() {
        FireStation fs = new FireStation(); fs.setAddress("Addr1"); fs.setStation(1);
        dataFile.setFireStations(List.of(fs));
        dataFile.setPersons(List.of()); // aucun habitant
        dataFile.setMedicalRecords(List.of());

        List<String> phones = service.getPhonesByStation(1);

        assertThat(phones).isEmpty();
    }
}
