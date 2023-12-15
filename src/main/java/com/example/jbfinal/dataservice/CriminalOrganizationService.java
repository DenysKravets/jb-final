package com.example.jbfinal.dataservice;

import com.example.jbfinal.data.CriminalOrganization;
import com.example.jbfinal.dataservice.query.CriminalOrganizationQueryFactory;
import com.example.jbfinal.dataservice.query.Query;
import com.example.jbfinal.parser.FormParser;
import com.example.jbfinal.repository.*;
import com.example.jbfinal.response.entity.CriminalOrganizationResponseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CriminalOrganizationService {

    private final CriminalOrganizationRepository criminalOrganizationRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    private final FormParser formParser;

    public CriminalOrganization getCriminalOrganizationById(Long id) {
        return criminalOrganizationRepository.findById(id).orElse(null);
    }

    public List<CriminalOrganizationResponseEntity> getCriminalOrganizationResponseEntities(String form) {

        return this.getCriminalOrganizations(form)
                .stream()
                .map(CriminalOrganizationResponseEntity::new)
                .toList();
    }

    @SuppressWarnings("unchecked")
    public List<CriminalOrganization> getCriminalOrganizations(String form) {

        Map<String, String> parameters = formParser.parseToMap(form);

        CriminalOrganizationQueryFactory criminalOrganizationQueryFactory
                = new CriminalOrganizationQueryFactory();
        Query<CriminalOrganization> criminalOrganizationQuery
                = criminalOrganizationQueryFactory.createQuery(parameters);

        return entityManager.createNativeQuery(
                criminalOrganizationQuery.getString(),
                criminalOrganizationQuery.queriesClass()
        ).getResultList();
    }

}
