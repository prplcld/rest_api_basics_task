package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.api.controller.TagController;
import com.epam.esm.restapibasics.api.hateoas.model.TagHateoasEntity;
import com.epam.esm.restapibasics.service.dto.TagDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class TagHateoasAssembler extends RepresentationModelAssemblerSupport<TagDto, TagHateoasEntity> {

    public TagHateoasAssembler() {
        super(TagController.class, TagHateoasEntity.class);
    }

    @Override
    public TagHateoasEntity toModel(TagDto entity) {
        TagHateoasEntity tagHateoasEntity = createModelWithId(entity.getId(), entity);
        tagHateoasEntity.add(LinkBuilder.buildControllerLink(getControllerClass()));
        tagHateoasEntity.add(LinkBuilder.buildMostUsedTagLink());
        tagHateoasEntity.setTag(entity);
        return tagHateoasEntity;
    }
}
