package com.example.jbfinal.controller;


import com.example.jbfinal.dataservice.CriminalOrganizationService;
import com.example.jbfinal.response.entity.CriminalOrganizationResponseEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController()
public class APIController {

    private final CriminalOrganizationService criminalOrganizationService;

    @PostMapping("/api/search-criminal-organizations")
    public List<CriminalOrganizationResponseEntity> searchCriminalOrganizations(@RequestBody String body) {

        return criminalOrganizationService.getCriminalOrganizationResponseEntities(body);
    }

}
