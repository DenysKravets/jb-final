package com.example.jbfinal.response.entity;

import lombok.Data;

@Data
public class CriminalOrganizationResponseEntity {

    private Long id;
    private String name;

    public CriminalOrganizationResponseEntity(com.example.jbfinal.data.CriminalOrganization crimeOrg) {
        this.id = crimeOrg.getId();
        this.name = crimeOrg.getName();
    }

}
