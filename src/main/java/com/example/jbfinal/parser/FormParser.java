package com.example.jbfinal.parser;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FormParser {

    public Map<String, String> parseToMap(String form) {

        return Stream.of(form.replace("+", " ")
                .split("&"))
                .map(s -> s.split("="))
                .filter(s -> s.length == 2)
                .filter(s -> !s[1].trim().equals(""))
                .collect(Collectors.toMap(
                        s -> s[0],
                        s -> s[1].trim(),
                        (s1, s2) -> String.format("%s,%s", s1, s2)
                ));
    }

}
