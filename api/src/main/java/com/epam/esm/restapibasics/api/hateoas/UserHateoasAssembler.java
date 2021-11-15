package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.api.controller.UserController;
import com.epam.esm.restapibasics.api.hateoas.model.UserHateoasEntity;
import com.epam.esm.restapibasics.service.dto.UserDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class UserHateoasAssembler extends RepresentationModelAssemblerSupport<UserDto, UserHateoasEntity> {

    public UserHateoasAssembler() {
        super(UserController.class, UserHateoasEntity.class);
    }

    @Override
    public UserHateoasEntity toModel(UserDto entity) {
        UserHateoasEntity userHateoasEntity = createModelWithId(entity.getId(), entity);
        userHateoasEntity.add(LinkBuilder.buildControllerLink(getControllerClass()));
        userHateoasEntity.setUser(entity);
        return userHateoasEntity;
    }
}
