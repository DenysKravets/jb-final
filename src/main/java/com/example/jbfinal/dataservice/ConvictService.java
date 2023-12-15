package com.example.jbfinal.dataservice;

import com.example.jbfinal.data.*;
import com.example.jbfinal.dataservice.query.ConvictQueryFactory;
import com.example.jbfinal.dataservice.query.Query;
import com.example.jbfinal.parser.FormParser;
import com.example.jbfinal.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    public void putInJail(Long id) {
        Convict convict = convictRepository.findById(id).orElse(null);
        assert convict != null;
        convict.setCaught(true);
        convictRepository.save(convict);
    }

    public void deleteIfDead(Long id) {
        Convict convict = convictRepository.findById(id).orElse(null);
        assert convict != null;
        // Delete only if dead
        if (convict.getDead())
            delete(convict);
    }

    private void delete(Convict convict) {

        // Remove accomplice connections
        convict.getAccomplices().forEach(acc -> {
            acc.getAccomplices().removeIf(possibleConnection -> possibleConnection.equals(convict));
            convictRepository.save(acc);
        });

        // Remove criminal organization connections
        convict.getCriminalOrganization().forEach(co -> {
            co.getConvict().removeIf(possibleConnection -> possibleConnection.equals(convict));
            criminalOrganizationRepository.save(co);
        });

        convictRepository.delete(convict);
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

    // Overloaded method including caught and dead
    public Convict createConvict(
            String name,
            String surname,
            String alias,
            double height,
            String hairColor,
            String eyeColor,
            String distinguishingFeatures,
            String citizenship,
            String placeAndTimeOfBirth,
            String lastPlaceOfHabitat,
            String languages,
            String criminalSpecialization,
            String lastCriminalCase,
            String criminalOrganization,
            boolean caught,
            boolean dead
    ) {
        Convict convict = createConvict(
                name, surname, alias, height, hairColor, eyeColor, distinguishingFeatures,
                citizenship, placeAndTimeOfBirth, lastPlaceOfHabitat, languages,
                criminalSpecialization, lastCriminalCase, criminalOrganization
        );
        convict.setCaught(caught);
        convict.setDead(dead);
        convictRepository.save(convict);
        return convictRepository.findById(convict.getId()).orElse(null);
    }

    public Convict createConvict(
            String name,
            String surname,
            String alias,
            double height,
            String hairColor,
            String eyeColor,
            String distinguishingFeatures,
            String citizenship,
            String placeAndTimeOfBirth,
            String lastPlaceOfHabitat,
            String languages,
            String criminalSpecialization,
            String lastCriminalCase,
            String criminalOrganization
    ) {

        // Poll all relevant data entries, inform if not present

        // To lowercase
        hairColor = hairColor.toLowerCase();
        HairColor hairColorObject = hairColorRepository.findByColor(hairColor);
        if (hairColorObject == null)
            throw new RuntimeException(String.format("Hair color (%s) is not present in database", hairColor));

        // To lowercase
        eyeColor = eyeColor.toLowerCase();
        EyeColor eyeColorObject = eyeColorRepository.findByColor(eyeColor);
        if (eyeColorObject == null)
            throw new RuntimeException(String.format("Eye color (%s) is not present in database", eyeColor));

        // To lowercase and capitalize
        citizenship = citizenship.substring(0, 1).toUpperCase() + citizenship.substring(1).toLowerCase();
        Country countryObject = countryRepository.findByCountry(citizenship);
        if (countryObject == null)
            throw new RuntimeException(String.format("Citizenship (%s) is not present in database", citizenship));

        // Split and parse languages, ',' is the delimiter
        List<Language> languageList = Arrays.stream(languages.split(","))
                .map(String::trim)
                .map(
                        // To lowercase and capitalize
                        language -> language.substring(0, 1).toUpperCase() + language.substring(1).toLowerCase()
                )
                .map(language -> {
                    Language languageObject = languageRepository.findByLanguage(language);
                    if (languageObject == null)
                        throw new RuntimeException(
                                String.format("Language (%s) is not present in database", language)
                        );
                    return languageObject;
                })
                .toList();
        if (languageList.size() == 0)
            throw new RuntimeException("Convict must speak at least one language");

        // Split and parse Criminal Organizations, ',' is the delimiter
        List<CriminalOrganization> crimeOrgList = Arrays.stream(criminalOrganization.split(","))
                .map(String::trim)
                .map(crimeOrg -> {
                    CriminalOrganization crimeOrgObject = criminalOrganizationRepository.findByName(crimeOrg);
                    if (crimeOrgObject == null)
                        throw new RuntimeException(
                                String.format("Criminal Organization (%s) is not present in database", crimeOrg)
                        );
                    return crimeOrgObject;
                })
                .toList();

        Convict convict = new Convict.Builder()
                .setName(name)
                .setSurname(surname)
                .setAlias(alias)
                .setHeight(height)
                .setHairColor(hairColorObject)
                .setEyeColor(eyeColorObject)
                .setDistinguishingFeatures(distinguishingFeatures)
                .setCitizenship(countryObject)
                .setPlaceAndTimeOfBirth(placeAndTimeOfBirth)
                .setLastPlaceOfHabitat(lastPlaceOfHabitat)
                .setLanguages(languageList)
                .setCriminalSpecialization(criminalSpecialization)
                .setLastCriminalCase(lastCriminalCase)
                .setCriminalOrganizations(crimeOrgList)
                .setCaught(false)
                .setDead(false)
                .build();

        // Save convict, find by name and surname, connect to crime organizations
        convictRepository.save(convict);

        convict = convictRepository.findByNameAndSurname(name, surname);

        final Convict finalConvict = convict;
        crimeOrgList.forEach(crimeOrg -> {
            crimeOrg.getConvict().add(finalConvict);
            criminalOrganizationRepository.save(crimeOrg);
        });

        return convict;
    }

    public void putConvictsInCahoots(Convict convict1, Convict convict2) {

        // Don't add convict to itself
        if (convict1.getId().equals(convict2.getId()))
            return;

        // Adding same accomplices will create faulty logic
        if (convict1.getAccomplices().contains(convict2) || convict2.getAccomplices().contains(convict1))
            return;

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
