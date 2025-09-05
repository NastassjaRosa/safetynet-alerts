package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.dto.PersonCoverageDTO;
import com.safetynet.safetynetalerts.dto.StationCoverageDTO;
import com.safetynet.safetynetalerts.model.DataFile;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class FireStationServiceTest {

    @Mock
    private DataRepository repo;

    @InjectMocks
    private FireStationService service;

    private DataFile dataFile;

    @BeforeEach
    void setUp() {
        dataFile = new DataFile();

        Person p1 = new Person(); p1.setFirstName("A"); p1.setLastName("One"); p1.setAddress("Addr1"); p1.setPhone("111");
        Person p2 = new Person(); p2.setFirstName("B"); p2.setLastName("Two"); p2.setAddress("Addr1"); p2.setPhone("222");

        FireStation fs = new FireStation(); fs.setAddress("Addr1"); fs.setStation(1);

        MedicalRecord mr1 = new MedicalRecord(); mr1.setFirstName("A"); mr1.setLastName("One"); mr1.setBirthdate("01/01/2000");
        MedicalRecord mr2 = new MedicalRecord(); mr2.setFirstName("B"); mr2.setLastName("Two"); mr2.setBirthdate("01/01/2010");

        dataFile.setPersons(List.of(p1, p2));
        dataFile.setFireStations(List.of(fs));
        dataFile.setMedicalRecords(List.of(mr1, mr2));

        when(repo.getDataFile()).thenReturn(dataFile);
    }

    @Test
    void getCoverageByStation_returnsPersonsAndCounts() {
        StationCoverageDTO dto = service.getCoverageByStation(1);
        assertThat(dto).isNotNull();
        assertThat(dto.getPersons()).hasSize(2);

        // one adult (2000) and one child (2010) depending on current year
        assertThat(dto.getAdultCount() + dto.getChildCount()).isEqualTo(dto.getPersons().size());
        // Person DTO fields
        PersonCoverageDTO p = dto.getPersons().get(0);
        assertThat(p.getFirstName()).isNotNull();
        assertThat(p.getAddress()).isNotNull();
    }


}
