package com.epam.esm.restapibasics.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagDto extends BaseDto {
    private Long id;
    private String name;

}
