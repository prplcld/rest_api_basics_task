package com.epam.esm.restapibasics.api.config;

import com.epam.esm.restapibasics.model.dao.OrderType;
import org.springframework.core.convert.converter.Converter;


public class OrderTypeConverter implements Converter<String, OrderType> {

    @Override
    public OrderType convert(String value) {
        return OrderType.valueOf(value.toUpperCase());
    }
}
