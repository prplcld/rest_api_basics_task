package com.epam.esm.restapibasics.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrderDto extends BaseDto {

    private long userId;

    private List<Long> certificateIds;

    private BigDecimal cost;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime purchaseDate;

}
