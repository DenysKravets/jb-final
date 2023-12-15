package com.example.jbfinal.repository;

import com.example.jbfinal.data.EyeColor;
import org.springframework.data.repository.CrudRepository;

public interface EyeColorRepository extends CrudRepository<EyeColor, Long> {

    EyeColor findByColor(String  color);
}
