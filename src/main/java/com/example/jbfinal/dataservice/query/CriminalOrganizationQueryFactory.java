package com.example.jbfinal.dataservice.query;

import com.example.jbfinal.data.CriminalOrganization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CriminalOrganizationQueryFactory extends AbstractQueryFactory<CriminalOrganization> {

    public CriminalOrganizationQueryFactory() {
        super(CriminalOrganization.class);
    }

    @Override
    protected void populateStringBuilder(StringBuilder stringBuilder, Map<String, String> parameters) {

        List<String> conditions = new ArrayList<>();

        // For testing remove later
        if (parameters.containsKey("id")) {
            Long id = Long.parseLong(parameters.get("id"));
            conditions.add(String.format("co.id = %d", id));
        }

        if (parameters.containsKey("name")) {
            String name = parameters.get("name");
            conditions.add(String.format("co.name LIKE '%%%s%%'", name)); // %%%s%% = *@{name}*
        }

        stringBuilder.append("SELECT * FROM criminal_organization AS co");

        // If no conditions present select all entries
        if (conditions.size() == 0) {
            return;
        }

        // Otherwise chain conditions
        stringBuilder.append(" WHERE ");
        stringBuilder.append(String.join(" AND ", conditions));

    }

}
