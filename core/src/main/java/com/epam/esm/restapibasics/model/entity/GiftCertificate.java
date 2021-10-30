package com.epam.esm.restapibasics.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GiftCertificate {

    private Long Id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}
