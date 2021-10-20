package com.epam.esm.restapibasics.service.dto;

import com.epam.esm.restapibasics.model.dao.OrderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchParamsModelDto {
    private String tagName;
    private String certificateName;
    private String certificateDescription;
    private OrderType orderByName;
    private OrderType orderByCreateDate;
}
