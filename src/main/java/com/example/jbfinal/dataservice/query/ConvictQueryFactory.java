package com.example.jbfinal.dataservice.query;

import com.example.jbfinal.data.Convict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConvictQueryFactory extends AbstractQueryFactory<Convict>{

    public ConvictQueryFactory() {
        super(Convict.class);
    }

    // Implementation for Convict conditional parameters
    @Override
    protected void populateStringBuilder(StringBuilder stringBuilder, Map<String, String> parameters) {

        List<String> conditions = new ArrayList<>();

        // For testing remove later
        if (parameters.containsKey("id")) {
            Long id = Long.parseLong(parameters.get("id"));
            conditions.add(String.format("c.id = %d", id));
        }

        if (parameters.containsKey("name")) {
            String name = parameters.get("name");
            conditions.add(String.format("c.name LIKE '%%%s%%'", name)); // %%%s%% = *@{name}*
        }

        if (parameters.containsKey("surname")) {
            String surname = parameters.get("surname");
            conditions.add(String.format("c.surname LIKE '%%%s%%'", surname)); //
        }

        if (parameters.containsKey("alias")) {
            String alias = parameters.get("alias");
            conditions.add(String.format("c.alias LIKE '%%%s%%'", alias)); //
        }

        if (parameters.containsKey("height")) {
            String height = parameters.get("height");
            conditions.add(String.format("c.height = %s", height)); //
        }

        if (parameters.containsKey("hairColor")) {
            String hairColor = parameters.get("hairColor");
            conditions.add(String.format("c.hair_color_id = %s", hairColor)); //
        }

        if (parameters.containsKey("eyeColor")) {
            String eyeColor = parameters.get("eyeColor");
            conditions.add(String.format("c.eye_color_id = %s", eyeColor)); //
        }

        if (parameters.containsKey("distinguishingFeatures")) {
            String distinguishingFeatures = parameters.get("distinguishingFeatures");
            conditions.add(String.format("c.distinguishing_features LIKE '%%%s%%'", distinguishingFeatures)); //
        }

        if (parameters.containsKey("citizenship")) {
            String citizenship = parameters.get("citizenship");
            conditions.add(String.format("c.citizenship_id = %s", citizenship)); //
        }

        if (parameters.containsKey("placeAndTimeOfBirth")) {
            String placeAndTimeOfBirth = parameters.get("placeAndTimeOfBirth");
            conditions.add(String.format("c.place_and_time_of_birth LIKE '%%%s%%'", placeAndTimeOfBirth)); //
        }

        if (parameters.containsKey("lastPlaceOfHabitat")) {
            String lastPlaceOfHabitat = parameters.get("lastPlaceOfHabitat");
            conditions.add(String.format("c.last_place_of_habitat LIKE '%%%s%%'", lastPlaceOfHabitat)); //
        }

        if (parameters.containsKey("languages")) {
            String languages = parameters.get("languages");
            conditions.add(createLanguagesCondition(languages));
        }

        if (parameters.containsKey("criminalSpecialization")) {
            String criminalSpecialization = parameters.get("criminalSpecialization");
            conditions.add(String.format("c.criminal_specialization LIKE '%%%s%%'", criminalSpecialization)); //
        }

        if (parameters.containsKey("lastCriminalCase")) {
            String lastCriminalCase = parameters.get("lastCriminalCase");
            conditions.add(String.format("c.last_criminal_case LIKE '%%%s%%'", lastCriminalCase)); //
        }

        if (parameters.containsKey("criminalOrganization")) {
            String criminalOrganization = parameters.get("criminalOrganization");
            conditions.add(createCriminalOrganizationCondition(criminalOrganization));
        }

        stringBuilder.append("SELECT * FROM convict AS c");

        // If no conditions present select all entries
        if (conditions.size() == 0) {
            return;
        }

        // Otherwise chain conditions
        stringBuilder.append(" WHERE ");
        stringBuilder.append(String.join(" AND ", conditions));

        System.out.println("############");
        System.out.println("############");
        System.out.println("############");
        System.out.println(stringBuilder);
        System.out.println("############");
        System.out.println("############");
        System.out.println("############");
    }

    /*
    select * FROM convict as c
	WHERE c.id IN (
		select cl.convict_id
			from convict_languages AS cl
				WHERE
					<id_1> IN (
						select cl_nested.languages_id from convict_languages as cl_nested
							where cl_nested.convict_id = cl.convict_id
					) AND
					<id_2> IN (
						select cl_nested.languages_id from convict_languages as cl_nested
							where cl_nested.convict_id = cl.convict_id
					)
					... (more conditions if present)
			)
			AND ... (more other conditions if present)
    */
    private String createLanguagesCondition(String languages) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("""
                c.id IN (
                		select cl.convict_id
                			from convict_languages AS cl
                				WHERE
                """);


        List<String> conditions = Stream.of(languages.split(","))
                .map(id -> String.format("""
                        %s IN (
                            select cl_nested.languages_id from convict_languages as cl_nested
                                where cl_nested.convict_id = cl.convict_id
                        )
                        """, id)
                ).toList();

        stringBuilder.append(String.join(" AND ", conditions));
        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    /*
    select * FROM convict as c
	WHERE c.id IN (
		select cco.convict_id
			from convict_criminal_organization AS cco
				WHERE
					<id_1> IN (
						select cco_nested.criminal_organization_id from convict_criminal_organization as cco_nested
							where cco_nested.convict_id = cco.convict_id
					) AND
					<id_2> IN (
						select cco_nested.criminal_organization_id from convict_criminal_organization as cco_nested
							where cco_nested.convict_id = cco.convict_id
					)
					... (more conditions if present)
			)
			AND ... (more other conditions if present)
    * */
    private String createCriminalOrganizationCondition(String criminalOrganization) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("""
                c.id IN (
                    select cco.convict_id
                        from convict_criminal_organization AS cco
                            WHERE
                """);


        List<String> conditions = Stream.of(criminalOrganization.split(","))
                .map(id -> String.format("""
                        %s IN (
                            select cco_nested.criminal_organization_id from convict_criminal_organization as cco_nested
                                where cco_nested.convict_id = cco.convict_id
                        )
                        """, id)
                ).toList();

        stringBuilder.append(String.join(" AND ", conditions));
        stringBuilder.append(")");

        return stringBuilder.toString();
    }


}
