package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.service.dto.BaseDto;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class LinkBuilder {

    private LinkBuilder(){

    }

    public static <T extends BaseDto> Link buildSelfLink(Class<?> controller, T dto) {
        return linkTo(controller)
                .slash(dto.getId())
                .withSelfRel();
    }

    public static Link buildControllerLink(Class<?> controller, String rel) {
        return linkTo(controller)
                .withRel(rel);
    }

    public static Link buildControllerLinkWithId(Class<?> controller, String rel, Long id) {
        return linkTo(controller)
                .slash(id)
                .withRel(rel);
    }
}
