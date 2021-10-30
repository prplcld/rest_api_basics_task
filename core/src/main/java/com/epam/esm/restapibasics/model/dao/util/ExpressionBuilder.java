package com.epam.esm.restapibasics.model.dao.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExpressionBuilder {
    private List<String> components = new ArrayList<>();
    private String initial;
    private String delimiter;

    public ExpressionBuilder(String initial, String delimiter) {
        this.initial = initial;
        this.delimiter = delimiter;
    }

    public void addComponent(String condition) {
        components.add(condition);
    }

    public String build() {
        if (components.isEmpty()) {
            return "";
        }

        StringBuilder clause = new StringBuilder(initial);
        Iterator<String> iterator = components.iterator();

        while (iterator.hasNext()) {
            String condition = iterator.next();
            clause.append(condition);

            if (iterator.hasNext()) {
                clause.append(delimiter);
            }
        }

        return clause.toString();
    }
}
