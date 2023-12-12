package com.example.jbfinal.dataservice.query;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Query<T> {

    private final String contents;
    private final Class<T> queryClass;

    public String getString() {
        return contents;
    }

    public Class<T> queriesClass() {
        return queryClass;
    }
}
