package com.example.jbfinal.dataservice.query;

import java.util.Map;

public abstract class AbstractQueryFactory <T> {

    private final Class<T> queryClass;

    protected AbstractQueryFactory(Class<T> queryClass) {
        this.queryClass = queryClass;
    }

    public Query<T> createQuery(Map<String, String> parameters) {

        StringBuilder stringBuilder = new StringBuilder();

        populateStringBuilder(stringBuilder, parameters);

        return new Query<>(stringBuilder.toString(), queryClass);
    }

    protected abstract void populateStringBuilder(StringBuilder stringBuilder, Map<String, String> parameters);

}
