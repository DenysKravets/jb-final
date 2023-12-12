package com.example.jbfinal.repository;

import com.example.jbfinal.data.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
}
