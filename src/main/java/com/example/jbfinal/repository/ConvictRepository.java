package com.example.jbfinal.repository;

import com.example.jbfinal.data.Convict;
import org.springframework.data.repository.CrudRepository;

public interface ConvictRepository extends CrudRepository<Convict, Long> {

    Convict findByNameAndSurname(String name, String surname);

}
