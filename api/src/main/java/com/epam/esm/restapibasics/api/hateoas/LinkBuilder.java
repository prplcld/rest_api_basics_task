package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.api.controller.TagController;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LinkBuilder {

    private LinkBuilder(){

    }

    private static final String resourceRel = "resource";
    private static final String mostUsedTag = "mostUsedTag";

    public static Link buildControllerLink(Class<?> controller) {
        return linkTo(controller)
                .withRel(resourceRel);
    }

    public static Link buildMostUsedTagLink() {
        return linkTo(methodOn(TagController.class).getMostWidelyTag())
                .withRel(mostUsedTag);
    }
}
