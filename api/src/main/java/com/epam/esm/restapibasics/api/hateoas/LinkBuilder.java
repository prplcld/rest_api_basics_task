package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.api.controller.GiftCertificateController;
import com.epam.esm.restapibasics.api.controller.OrderController;
import com.epam.esm.restapibasics.api.controller.TagController;
import com.epam.esm.restapibasics.api.controller.UserController;
import com.epam.esm.restapibasics.service.dto.BaseDto;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class LinkBuilder {

    private LinkBuilder(){

    }

    private static final String resourceRel = "resource";

    public static <T extends BaseDto> List<Link> buildLinks(T dto) {
        DtoType type = DtoType.get(dto.getClass().getSimpleName().toUpperCase());
        List<Link> links = new ArrayList<>();
        switch (type) {
            case TAG_DTO -> {
                links.add(buildSelfLink(TagController.class, dto));
                links.add(buildControllerLink(TagController.class, resourceRel));
            }
            case GIFT_CERTIFICATE_DTO -> {
                links.add(buildSelfLink(GiftCertificateController.class, dto));
                links.add(buildControllerLink(GiftCertificateController.class, resourceRel));
            }
            case USER_DTO -> {
                links.add(buildSelfLink(UserController.class, dto));
                links.add(buildControllerLink(UserController.class, resourceRel));
            }
            case ORDER_DTO -> {
                links.add(buildSelfLink(OrderController.class, dto));
                links.add(buildControllerLink(OrderController.class, resourceRel));
            }
        }
        return links;
    }

    private static <T extends BaseDto> Link buildSelfLink(Class<?> controller, T dto) {
        return linkTo(controller)
                .slash(dto.getId())
                .withSelfRel();
    }

    private static Link buildControllerLink(Class<?> controller, String rel) {
        return linkTo(controller)
                .withRel(rel);
    }

    private static Link buildControllerLinkWithId(Class<?> controller, String rel, Long id) {
        return linkTo(controller)
                .slash(id)
                .withRel(rel);
    }

    private enum DtoType {
        TAG_DTO("TAGDTO"),
        GIFT_CERTIFICATE_DTO("GIFTCERTIFICATEDTO"),
        USER_DTO("USERDTO"),
        ORDER_DTO("ORDERDTO");

        private String name;

        private static final Map<String,DtoType> ENUM_MAP;

        DtoType (String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        static  {
            Map<String,DtoType> map = new ConcurrentHashMap<String, DtoType>();
            for (DtoType instance : DtoType.values()) {
                map.put(instance.getName().toLowerCase(),instance);
            }
            ENUM_MAP = Collections.unmodifiableMap(map);
        }

        public static DtoType get (String name) {
            return ENUM_MAP.get(name.toLowerCase());
        }
    }
}
