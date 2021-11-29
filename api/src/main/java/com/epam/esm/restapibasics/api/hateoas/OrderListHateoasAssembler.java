package com.epam.esm.restapibasics.api.hateoas;

import com.epam.esm.restapibasics.api.controller.OrderController;
import com.epam.esm.restapibasics.api.hateoas.model.OrderListHateoasEntity;
import com.epam.esm.restapibasics.service.dto.OrderDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.List;

public class OrderListHateoasAssembler extends RepresentationModelAssemblerSupport<List<OrderDto>, OrderListHateoasEntity> {

    public OrderListHateoasAssembler() {
        super(OrderController.class, OrderListHateoasEntity.class);
    }

    @Override
    public OrderListHateoasEntity toModel(List<OrderDto> entity) {
        OrderListHateoasEntity orderListHateoasEntity = new OrderListHateoasEntity(entity);
        orderListHateoasEntity.add(LinkBuilder.buildControllerLink(getControllerClass()));
        return orderListHateoasEntity;
    }
}
