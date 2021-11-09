package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.service.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HateoasListEntity<T extends BaseDto> extends RepresentationModel<HateoasEntity<T>> {
    private List<T> data;

    public static <T extends BaseDto> HateoasListEntity<T> build(List<T> object, Class<?> controller) {
        HateoasListEntity<T> hateoasEntity = new HateoasListEntity<>(object);
        hateoasEntity.add(LinkBuilder.buildListLinks(controller));
        return hateoasEntity;
    }
}
