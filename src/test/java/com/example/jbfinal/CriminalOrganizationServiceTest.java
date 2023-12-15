package com.example.jbfinal;

import com.example.jbfinal.cleaner.DatabaseCleanerExtension;
import com.example.jbfinal.dataservice.CriminalOrganizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(DatabaseCleanerExtension.class)
public class CriminalOrganizationServiceTest {

    @Autowired
    CriminalOrganizationService criminalOrganizationService;

    @Test
    public void testCriminalOrganizationByName() {

        int actualValue = 0;
        int expectedValue = 0;

        actualValue = criminalOrganizationService.getCriminalOrganizations("name=Boys").size();
        expectedValue = 1;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

}
