package com.example.jbfinal.response.entity;

import com.example.jbfinal.data.CriminalOrganization;
import lombok.Data;

@Data
public class CriminalOrganizationResponseEntity {

    private final Long id;
    private final String name;

    public CriminalOrganizationResponseEntity(CriminalOrganization crimeOrg) {
        this.id = crimeOrg.getId();
        this.name = crimeOrg.getName();
    }

}
