package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.api.controller.UserController;
import com.epam.esm.restapibasics.api.hateoas.model.UserListHateoasEntity;
import com.epam.esm.restapibasics.service.dto.UserDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.List;

public class UserListHateoasAssembler extends RepresentationModelAssemblerSupport<List<UserDto>, UserListHateoasEntity> {

    public UserListHateoasAssembler() {
        super(UserController.class, UserListHateoasEntity.class);
    }

    @Override
    public UserListHateoasEntity toModel(List<UserDto> entity) {
        UserListHateoasEntity userListHateoasEntity = new UserListHateoasEntity(entity);
        userListHateoasEntity.add(LinkBuilder.buildControllerLink(getControllerClass()));
        return userListHateoasEntity;
    }
}
