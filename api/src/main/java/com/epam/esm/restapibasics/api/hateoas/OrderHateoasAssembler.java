package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.api.controller.OrderController;
import com.epam.esm.restapibasics.api.hateoas.model.OrderHateoasEntity;
import com.epam.esm.restapibasics.service.dto.OrderDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class OrderHateoasAssembler extends RepresentationModelAssemblerSupport<OrderDto, OrderHateoasEntity> {

    public OrderHateoasAssembler() {
        super(OrderController.class, OrderHateoasEntity.class);
    }

    @Override
    public OrderHateoasEntity toModel(OrderDto entity) {
        OrderHateoasEntity orderHateoasEntity = createModelWithId(entity.getId(), entity);
        orderHateoasEntity.add(LinkBuilder.buildControllerLink(getControllerClass()));
        orderHateoasEntity.setOrder(entity);
        return orderHateoasEntity;
    }
}
