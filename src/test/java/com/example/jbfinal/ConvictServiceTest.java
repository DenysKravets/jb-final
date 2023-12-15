package com.example.jbfinal;

import com.example.jbfinal.cleaner.DatabaseCleanerExtension;
import com.example.jbfinal.data.Convict;
import com.example.jbfinal.dataservice.ConvictService;
import com.example.jbfinal.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(DatabaseCleanerExtension.class)
public class ConvictServiceTest {

    @Autowired
    private ConvictService convictService;

    @Autowired
    private ConvictRepository convictRepository;
    @Autowired
    private CriminalOrganizationRepository criminalOrganizationRepository;
    @Autowired
    private HairColorRepository hairColorRepository;
    @Autowired
    private EyeColorRepository eyeColorRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @Test
    public void testCreateConvict() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        List<Convict> convictList = new ArrayList<>();
        convictRepository.findAll().forEach(convictList::add);

        actualValue = convictList.size();
        expectedValue = 1;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testCreateConvictMultiple() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Jackly",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        List<Convict> convictList = new ArrayList<>();
        convictRepository.findAll().forEach(convictList::add);

        actualValue = convictList.size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testCahoots() {

        String actualValue = null;
        String expectedValue = null;

        Convict convict1 = convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        Convict convict2 = convictService.createConvict(
                "Jackly",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.putConvictsInCahoots(convict1, convict2);

        convict1 = convictService.getConvictById(convict1.getId());
        convict2 = convictService.getConvictById(convict2.getId());

        actualValue = convict1.getAccomplices().get(0).getName();
        expectedValue = "Jackly";

        assertThat(actualValue).isEqualTo(expectedValue);

        actualValue = convict2.getAccomplices().get(0).getName();
        expectedValue = "Jack";

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testCreateConvictCrimeOrgConnections() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Jackly",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Smith",
                "Will",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );



        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvicts() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Jackly",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Smith",
                "Will",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        actualValue = convictService.getConvicts("").size();
        expectedValue = 3;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByName() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Smith",
                "Will",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        actualValue = convictService.getConvicts("name=smith").size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsBySurname() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        actualValue = convictService.getConvicts("surname=bowley").size();
        expectedValue = 1;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByAlias() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        actualValue = convictService.getConvicts("alias=Tiny Anvil").size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByHeight() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.73,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        actualValue = convictService.getConvicts("height=1.83").size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByHairColor() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.73,
                "brown",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        String hairColorId = Long.toString(hairColorRepository.findByColor("blond").getId());
        actualValue = convictService.getConvicts("hairColor=" + hairColorId).size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByEyeColor() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.73,
                "brown",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "blue",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        String id = Long.toString(eyeColorRepository.findByColor("amber").getId());
        actualValue = convictService.getConvicts("eyeColor=" +id).size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByDistinguishingFeatures() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.73,
                "brown",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "left eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "blue",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        String parameter = "right eye is missing";
        actualValue = convictService.getConvicts("distinguishingFeatures=" + parameter).size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByCitizenship() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.73,
                "brown",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "China",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "blue",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        String id = Long.toString(countryRepository.findByCountry("Singapore").getId());
        actualValue = convictService.getConvicts("citizenship=" +id).size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByPlaceAndTimeOfBirth() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.73,
                "brown",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "left eye is missing",
                "Singapore",
                "China 1999",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "blue",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        String parameter = "Singapore 1983";
        actualValue = convictService.getConvicts("placeAndTimeOfBirth=" + parameter).size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByLastPlaceOfHabitat() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.73,
                "brown",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Morocco",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "left eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "blue",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        String parameter = "Singapore";
        actualValue = convictService.getConvicts("lastPlaceOfHabitat=" + parameter).size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByLanguage() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.73,
                "brown",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "China",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "blue",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        String id1 = Long.toString(languageRepository.findByLanguage("English").getId());
        String id2 = Long.toString(languageRepository.findByLanguage("Chinese").getId());
        actualValue = convictService.getConvicts("languages=" +id1 + "&" + "languages=" +id2).size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByCriminalSpecialization() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.73,
                "brown",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Morocco",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "left eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to not get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "blue",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        String parameter = "first one to get shot";
        actualValue = convictService.getConvicts("criminalSpecialization=" + parameter).size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByLastCriminalCase() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.73,
                "brown",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Morocco",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from itself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "left eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "blue",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        String parameter = "Stole chicken from himself";
        actualValue = convictService.getConvicts("lastCriminalCase=" + parameter).size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }

    @Test
    public void testGetConvictsByCrimeOrg() {

        int actualValue = 0;
        int expectedValue = 0;

        convictService.createConvict(
                "Jack",
                "Bowley",
                "Tiny Anvil",
                1.73,
                "brown",
                "amber",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Smith",
                "Bowl",
                "Huge Anvil",
                1.83,
                "blond",
                "amber",
                "right eye is missing",
                "China",
                "Singapore 1983",
                "Singapore",
                "English, Chinese, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys, Pizza Connection"
        );

        convictService.createConvict(
                "Amanda",
                "Bowl",
                "Tiny Anvil",
                1.83,
                "blond",
                "blue",
                "right eye is missing",
                "Singapore",
                "Singapore 1983",
                "Singapore",
                "English, Vietnamese",
                "first one to get shot",
                "Stole chicken from himself",
                "Jackson Street Boys"
        );

        String id1 = Long.toString(criminalOrganizationRepository.findByName("Jackson Street Boys").getId());
        String id2 = Long.toString(criminalOrganizationRepository.findByName("Pizza Connection").getId());
        actualValue = convictService.getConvicts("criminalOrganization=" +id1
                + "&" + "criminalOrganization=" +id2).size();
        expectedValue = 2;

        assertThat(actualValue).isEqualTo(expectedValue);

    }
}
