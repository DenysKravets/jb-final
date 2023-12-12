package com.example.jbfinal.dataservice;

import com.example.jbfinal.data.*;
import com.example.jbfinal.dataservice.query.ConvictQueryFactory;
import com.example.jbfinal.dataservice.query.Query;
import com.example.jbfinal.parser.FormParser;
import com.example.jbfinal.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ConvictService {

    private final ConvictRepository convictRepository;
    private final CountryRepository countryRepository;
    private final CriminalOrganizationRepository criminalOrganizationRepository;
    private final EyeColorRepository eyeColorRepository;
    private final HairColorRepository hairColorRepository;
    private final LanguageRepository languageRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    private final FormParser formParser;

    public Convict getConvictById(Long id) {
        return convictRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("unchecked")
    public List<Convict> getConvicts(String form) {

        Map<String, String> parameters = formParser.parseToMap(form);

        ConvictQueryFactory convictQueryFactory = new ConvictQueryFactory();
        Query<Convict> convictQuery = convictQueryFactory.createQuery(parameters);

        return entityManager.createNativeQuery(
                convictQuery.getString(),
                convictQuery.queriesClass()
        ).getResultList();
    }

    @PostConstruct
    public void createTestData() {

        createTestAuxiliaryData();
        createTestConvicts();
    }

    private void createTestAuxiliaryData() {

        // Create a set list of possible hair colors
        List.of(new String[]{
                "black",
                "brown",
                "blond",
                "white",
                "red"
        }).forEach(this::saveHairColor);


        // Create a set list of possible eye colors
        List.of(new String[]{
                "amber",
                "blue",
                "brown",
                "gray",
                "green",
                "hazel",
                "red"
        }).forEach(this::saveEyeColor);

        // Create a set list of possible counties
        List.of(new String[]{
                "Japan",
                "China",
                "Korea",
                "Vietnam",
                "Australia",
                "Singapore",
                "Taiwan"
        }).forEach(this::saveCountry);

        // Create a set list of possible languages
        List.of(new String[]{
                "Japanese",
                "Chinese",
                "Korean",
                "Vietnamese",
                "English"
        }).forEach(this::saveLanguage);

        // Create a set list of possible criminal organizations
        List.of(new String[]{
                "Jackson Street Boys",
                "Luen Group",
                "Big Circle Gang",
                "Black Dragons",
                "Celestial Alliance",
                "Black Tuna Gang",
                "Pizza Connection"
        }).forEach(this::saveCriminalOrganization);

    }

    private void saveHairColor(String color) {
        HairColor hairColor = new HairColor();
        hairColor.setColor(color);
        hairColorRepository.save(hairColor);
    }

    private void saveEyeColor(String color) {
        EyeColor eyeColor = new EyeColor();
        eyeColor.setColor(color);
        eyeColorRepository.save(eyeColor);
    }

    private void saveCountry(String name) {
        Country country = new Country();
        country.setCountry(name);
        countryRepository.save(country);
    }

    private void saveLanguage(String name) {
        Language language = new Language();
        language.setLanguage(name);
        languageRepository.save(language);
    }

    private void saveCriminalOrganization(String name) {
        CriminalOrganization criminalOrganization = new CriminalOrganization();
        criminalOrganization.setName(name);
        criminalOrganizationRepository.save(criminalOrganization);
    }

    private void createTestConvicts() {

        // Find a way to populate database in an easier way

        // Create Convict 1

        Convict convict1 = new Convict.Builder()
                .setName("Jack")
                .setSurname("Bowley")
                .setAlias("Tiny Anvil")
                .setHeight(1.83)
                .setHairColor(hairColorRepository.findById(3L).orElse(null)) // Blond
                .setEyeColor(eyeColorRepository.findById(1L).orElse(null)) // Amber
                .setDistinguishingFeatures("right eye is missing")
                .setCitizenship(countryRepository.findById(6L).orElse(null)) // Singapore
                .setPlaceAndTimeOfBirth("Singapore 1983")
                .setLastPlaceOfHabitat("Singapore")
                .setLanguages(List.of(new Language[]{
                        languageRepository.findById(5L).orElse(null), // English
                        languageRepository.findById(2L).orElse(null) // Chinese
                }))
                .setCriminalSpecialization("first one to get shot")
                .setLastCriminalCase("Stole chicken from himself")
                .setCriminalOrganizations(List.of(new CriminalOrganization[] {
                        criminalOrganizationRepository.findById(1L).orElse(null) // Jackson Street Boys
                }))
                .build();

        convictRepository.save(convict1);
        convict1 = convictRepository.findById(1L).orElse(null);
        CriminalOrganization criminalOrganization = criminalOrganizationRepository.findById(1L).orElse(null);
        criminalOrganization.getConvict().add(convict1);
        criminalOrganizationRepository.save(criminalOrganization);



        // Create Convict 2

        Convict convict2 = new Convict.Builder()
                .setName("Ting")
                .setSurname("Yuan")
                .setAlias("Fist of Han")
                .setHeight(1.75)
                .setHairColor(hairColorRepository.findById(1L).orElse(null)) // Black
                .setEyeColor(eyeColorRepository.findById(3L).orElse(null)) // Brown
                .setDistinguishingFeatures("tattoo of blue moon")
                .setCitizenship(countryRepository.findById(2L).orElse(null)) // Shenzhen
                .setPlaceAndTimeOfBirth("Shenzhen 1991")
                .setLastPlaceOfHabitat("Shenzhen")
                .setLanguages(List.of(new Language[]{
                        languageRepository.findById(2L).orElse(null) // Chinese
                }))
                .setCriminalSpecialization("cook")
                .setLastCriminalCase("Forgot who Mao is")
                .setCriminalOrganizations(List.of(new CriminalOrganization[] {
                        criminalOrganizationRepository.findById(1L).orElse(null), // Jackson Street Boys
                        criminalOrganizationRepository.findById(2L).orElse(null) // Luen Group
                }))
                .build();


        convictRepository.save(convict2);
        convict2 = convictRepository.findById(2L).orElse(null);
        criminalOrganization = criminalOrganizationRepository.findById(1L).orElse(null);
        criminalOrganization.getConvict().add(convict2);
        criminalOrganizationRepository.save(criminalOrganization);

        criminalOrganization = criminalOrganizationRepository.findById(2L).orElse(null);
        criminalOrganization.getConvict().add(convict2);
        criminalOrganizationRepository.save(criminalOrganization);

        // Connect Convict 1 with 2

        convict1 = convictRepository.findById(1L).orElse(null);
        convict2 = convictRepository.findById(2L).orElse(null);
        assert convict1 != null;
        assert convict2 != null;
        putConvictsInCahoots(convict1, convict2);

        // Create Convict 3

        convict1 = new Convict.Builder()
                .setName("Li")
                .setSurname("Shi")
                .setAlias("Chin")
                .setHeight(1.69)
                .setHairColor(hairColorRepository.findById(4L).orElse(null)) // White
                .setEyeColor(eyeColorRepository.findById(7L).orElse(null)) // Red
                .setDistinguishingFeatures("very small ears")
                .setCitizenship(countryRepository.findById(4L).orElse(null)) // Vietnam
                .setPlaceAndTimeOfBirth("Hanoi 1971")
                .setLastPlaceOfHabitat("Hanoi")
                .setLanguages(List.of(new Language[]{
                        languageRepository.findById(4L).orElse(null) // Vietnamese
                }))
                .setCriminalSpecialization("crime engagement server")
                .setLastCriminalCase("disarmed robbery")
                .setCriminalOrganizations(List.of(new CriminalOrganization[] {
                        criminalOrganizationRepository.findById(6L).orElse(null) // Black Tuna Gang
                }))
                .build();

        convictRepository.save(convict1);
        convict1 = convictRepository.findById(3L).orElse(null);
        criminalOrganization = criminalOrganizationRepository.findById(6L).orElse(null);
        criminalOrganization.getConvict().add(convict1);
        criminalOrganizationRepository.save(criminalOrganization);

        // Connect Convict 2 with 3
        convict1 = convictRepository.findById(2L).orElse(null);
        convict2 = convictRepository.findById(3L).orElse(null);
        assert convict1 != null;
        assert convict2 != null;
        putConvictsInCahoots(convict1, convict2);

        // Show results
        System.out.println();
        System.out.println(convictRepository.findById(1L).orElse(null));
        System.out.println();
        System.out.println(convictRepository.findById(2L).orElse(null));
        System.out.println();
        System.out.println(convictRepository.findById(3L).orElse(null));
        System.out.println();

    }

    private void putConvictsInCahoots(Convict convict1, Convict convict2) {

        long id1 = convict1.getId();
        long id2 = convict2.getId();

        convict1.getAccomplices().add(convict2);

        convictRepository.save(convict1);

        // Read Convict 2 update to add Convict 1 as accomplice

        convict1 = convictRepository.findById(id1).orElse(null);
        convict2 = convictRepository.findById(id2).orElse(null);

        assert convict2 != null;
        convict2.getAccomplices().add(convict1);

        convictRepository.save(convict2);

    }

}
