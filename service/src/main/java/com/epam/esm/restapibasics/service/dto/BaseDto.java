package com.epam.esm.restapibasics.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BaseDto {
    @JsonProperty("id")
    private Long id;
}
