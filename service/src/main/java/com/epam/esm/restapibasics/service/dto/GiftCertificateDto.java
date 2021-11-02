package com.epam.esm.restapibasics.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GiftCertificateDto extends BaseDto {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdateDate;

    private List<TagDto> tags = new ArrayList<>();
}
