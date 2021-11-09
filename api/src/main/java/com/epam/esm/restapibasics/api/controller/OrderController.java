package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.api.hateoas.HateoasEntity;
import com.epam.esm.restapibasics.api.hateoas.HateoasListEntity;
import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.service.OrderService;
import com.epam.esm.restapibasics.service.dto.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieve all orders or orders of specified user.
     *
     * @param userId user id (optional)
     * @return JSON {@link ResponseEntity} object that contains list of {@link HateoasListEntity} objects
     */
    @GetMapping
    public ResponseEntity<HateoasListEntity<OrderDto>> getOrders(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer amount) {
        Paginator paginator = new Paginator(page, amount);
        List<OrderDto> orders = userId != null
                ? orderService.findByUser(userId, paginator)
                : orderService.findAll(paginator);

        HateoasListEntity<OrderDto> hateoasListEntity = HateoasListEntity.build(orders, OrderController.class);
        return new ResponseEntity<>(hateoasListEntity, HttpStatus.OK);
    }

    /**
     * Retrieve order by its unique id.
     *
     * @param id order id
     * @return JSON {@link ResponseEntity} object that contains {@link HateoasEntity} object
     */
    @GetMapping("/{id}")
    public ResponseEntity<HateoasEntity<OrderDto>> getOrder(@PathVariable("id") long id) {
        OrderDto orderDto = orderService.findById(id);
        HateoasEntity<OrderDto> hateoasEntity = HateoasEntity.build(orderDto);
        return new ResponseEntity<>(hateoasEntity, HttpStatus.OK);
    }

    /**
     * Create an order.
     *
     * @param orderDto {@link OrderDto} instance (only {@code userId} and {@code certificateId} are required)
     * @return JSON {@link ResponseEntity} object that contains {@link HateoasEntity} object
     */
    @PostMapping
    public ResponseEntity<HateoasEntity<OrderDto>> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrderDto = orderService.createOrder(orderDto);
        HateoasEntity<OrderDto> hateoasEntity = HateoasEntity.build(orderDto);
        return new ResponseEntity<>(hateoasEntity, HttpStatus.CREATED);
    }
}
