package com.example.jbfinal.repository;

import com.example.jbfinal.data.HairColor;
import org.springframework.data.repository.CrudRepository;

public interface HairColorRepository extends CrudRepository<HairColor, Long> {

    HairColor findByColor(String  color);
}
