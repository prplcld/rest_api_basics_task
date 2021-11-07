package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.api.hateoas.HateoasEntity;
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

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer amount) {
        Paginator paginator = new Paginator(page, amount);
        List<OrderDto> orders = userId != null
                ? orderService.findByUser(userId, paginator)
                : orderService.findAll(paginator);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HateoasEntity<OrderDto>> getOrder(@PathVariable("id") long id) {
        OrderDto orderDto = orderService.findById(id);
        HateoasEntity<OrderDto> hateoasEntity = HateoasEntity.build(orderDto);
        return new ResponseEntity<>(hateoasEntity, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HateoasEntity<OrderDto>> makeOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrderDto = orderService.createOrder(orderDto);
        HateoasEntity<OrderDto> hateoasEntity = HateoasEntity.build(orderDto);
        return new ResponseEntity<>(hateoasEntity, HttpStatus.CREATED);
    }
}
