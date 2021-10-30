package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.service.dto.BaseDto;
import org.springframework.hateoas.RepresentationModel;

public class HateoasEntity<T extends BaseDto> extends RepresentationModel<HateoasEntity<T>> {
    private T dto;

    public static <T extends BaseDto> HateoasEntity<T> build(T object) {
        HateoasEntity<T> hateoasEntity = new HateoasEntity<>();
        //TODO add links to entity
        return hateoasEntity;
    }
}
