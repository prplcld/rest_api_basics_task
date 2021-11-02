package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.service.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HateoasEntity<T extends BaseDto> extends RepresentationModel<HateoasEntity<T>> {
    private T dto;

    public static <T extends BaseDto> HateoasEntity<T> build(T object) {
        HateoasEntity<T> hateoasEntity = new HateoasEntity<>(object);
        //TODO add links to entity
        return hateoasEntity;
    }
}
