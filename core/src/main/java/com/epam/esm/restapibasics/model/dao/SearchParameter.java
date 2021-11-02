package com.epam.esm.restapibasics.model.dao;

import java.util.List;

public class SearchParameter {
    private SearchParameterType type;
    private List<String> value;

    public SearchParameter(SearchParameterType type, List<String> value) {
        this.type = type;
        this.value = value;
    }

    public SearchParameterType getType() {
        return type;
    }

    public void setType(SearchParameterType type) {
        this.type = type;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}
