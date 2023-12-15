package com.example.jbfinal.test;

import com.example.jbfinal.data.*;
import com.example.jbfinal.dataservice.ConvictService;
import com.example.jbfinal.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

@Service
@AllArgsConstructor
public class TestDataGenerationService {

    private final CountryRepository countryRepository;
    private final CriminalOrganizationRepository criminalOrganizationRepository;
    private final EyeColorRepository eyeColorRepository;
    private final HairColorRepository hairColorRepository;
    private final LanguageRepository languageRepository;

    private final ConvictService convictService;

    private final Environment environment;

    @PostConstruct
    public void createMockData() throws Exception {
        createTestAuxiliaryData();
        // Disable random mock data generation for tests
        if (!environment.matchesProfiles("test"))
            createTestConvicts();
    }

    public void createTestAuxiliaryData() {

        // Create a set list of possible hair colors
        List.of(new String[]{
                "black",
                "brown",
                "blond",
                "white",
                "red"
        }).forEach(color -> {
            HairColor hairColor = new HairColor();
            hairColor.setColor(color);
            hairColorRepository.save(hairColor);
        });


        // Create a set list of possible eye colors
        List.of(new String[]{
                "amber",
                "blue",
                "brown",
                "gray",
                "green",
                "hazel",
                "red"
        }).forEach(color -> {
            EyeColor eyeColor = new EyeColor();
            eyeColor.setColor(color);
            eyeColorRepository.save(eyeColor);
        });

        // Create a set list of possible counties
        List.of(new String[]{
                "Japan",
                "China",
                "Korea",
                "Vietnam",
                "Australia",
                "Singapore",
                "Taiwan"
        }).forEach(name -> {
            Country country = new Country();
            country.setCountry(name);
            countryRepository.save(country);
        });

        // Create a set list of possible languages
        List.of(new String[]{
                "Japanese",
                "Chinese",
                "Korean",
                "Vietnamese",
                "English"
        }).forEach(name -> {
            Language language = new Language();
            language.setLanguage(name);
            languageRepository.save(language);
        });

        // Create a set list of possible criminal organizations
        List.of(new String[]{
                "Jackson Street Boys",
                "Luen Group",
                "Big Circle Gang",
                "Black Dragons",
                "Celestial Alliance",
                "Black Tuna Gang",
                "Pizza Connection"
        }).forEach(name -> {
            CriminalOrganization criminalOrganization = new CriminalOrganization();
            criminalOrganization.setName(name);
            criminalOrganizationRepository.save(criminalOrganization);
        });

    }

    private void createTestConvicts() throws Exception {

        final int MAX_NUMBER_OF_CONVICTS = 50;

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("MOCK_DATA.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        }

        List<Language> listOfAllLanguages = new ArrayList<>();
        languageRepository.findAll().forEach(listOfAllLanguages::add);

        List<CriminalOrganization> listOfAllCriminalOrgs = new ArrayList<>();
        criminalOrganizationRepository.findAll().forEach(listOfAllCriminalOrgs::add);

        // Limit amount of data
        records = records.subList(0, MAX_NUMBER_OF_CONVICTS - 1);

        List<Convict> createdConvicts = records
                .stream()
                .map(line ->
                        convictService.createConvict(
                            line.get(0).trim(),
                            line.get(1).trim(),
                            line.get(2).trim(),
                            Double.parseDouble(line.get(3).trim()),
                            line.get(4).trim(),
                            line.get(5).trim(),
                            line.get(6).trim(),
                            line.get(7).trim(),
                            line.get(8).trim(),
                            line.get(9).trim(),
                            generateRandomListOfLanguages(listOfAllLanguages),
                            line.get(11).trim(),
                            line.get(12).trim(),
                            generateRandomListOfCriminalOrganizations(listOfAllCriminalOrgs),
                            false,
                            generateDeadOrAlive()
                     )
                )
                .toList();

        generateAccomplicesRandomly(createdConvicts);
    }

    private boolean generateDeadOrAlive() {
        return Math.random() > 0.75;
    }

    private void generateAccomplicesRandomly(List<Convict> convictList) {

        for (Convict convictMain: convictList) {

            int budget = 10;
            int sum = 0;

            while (sum < budget) {

                // Update each run
                convictMain = convictService.getConvictById(convictMain.getId());

                sum += getRandomNumberFromZeroTo(budget) + 1;
                Convict toAddConvict = convictList.get(getRandomNumberFromZeroTo(convictList.size() - 1));
                // Update each run
                toAddConvict = convictService.getConvictById(toAddConvict.getId());

                convictService.putConvictsInCahoots(convictMain, toAddConvict);

            }
        }
    }

    private String generateRandomListOfLanguages(List<Language> languageList) {

        Set<Language> languageSet = new HashSet<>();

        int budget = languageList.size();
        int sum = 0;

        while (sum < budget) {
            sum += getRandomNumberFromZeroTo(budget) + 1;
            languageSet.add(languageList.get(getRandomNumberFromZeroTo(budget - 1)));
        }

        return String.join(",", languageSet.stream().map(Language::getLanguage).toList());
    }

    private String generateRandomListOfCriminalOrganizations(List<CriminalOrganization> crimeOrgList) {

        Set<CriminalOrganization> crimeOrgSet = new HashSet<>();

        int budget = crimeOrgList.size();
        int sum = 0;

        while (sum < budget) {
            sum += getRandomNumberFromZeroTo(budget) + 1;
            crimeOrgSet.add(crimeOrgList.get(getRandomNumberFromZeroTo(budget - 1)));
        }

        return String.join(",", crimeOrgSet.stream().map(CriminalOrganization::getName).toList());
    }

    private int getRandomNumberFromZeroTo(int max) {
        Random random = new Random();
        return random.ints(0, max)
                .findFirst()
                .orElse(max);
    }



}
