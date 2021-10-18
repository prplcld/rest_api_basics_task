package com.epam.esm.rest_api_basics.api.controller;

import java.beans.PropertyEditorSupport;

public class SortByEditor extends PropertyEditorSupport {
    public void setAsText(String text) {
        setValue(SortBy.valueOf(text.toUpperCase()));
    }
}
