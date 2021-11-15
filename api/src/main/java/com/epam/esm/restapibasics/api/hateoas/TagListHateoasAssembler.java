package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.api.controller.TagController;
import com.epam.esm.restapibasics.api.hateoas.model.TagListHateoasEntity;
import com.epam.esm.restapibasics.service.dto.TagDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.List;

public class TagListHateoasAssembler extends RepresentationModelAssemblerSupport<List<TagDto>, TagListHateoasEntity> {

    public TagListHateoasAssembler() {
        super(TagController.class, TagListHateoasEntity.class);
    }

    @Override
    public TagListHateoasEntity toModel(List<TagDto> entity) {
        TagListHateoasEntity tagListHateoasEntity = new TagListHateoasEntity(entity);
        tagListHateoasEntity.add(LinkBuilder.buildControllerLink(getControllerClass()));
        tagListHateoasEntity.add(LinkBuilder.buildMostUsedTagLink());
        return tagListHateoasEntity;
    }
}
