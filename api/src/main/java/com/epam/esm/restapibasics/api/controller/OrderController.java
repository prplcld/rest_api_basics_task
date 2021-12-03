package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.api.hateoas.OrderHateoasAssembler;
import com.epam.esm.restapibasics.api.hateoas.OrderListHateoasAssembler;
import com.epam.esm.restapibasics.api.hateoas.model.OrderHateoasEntity;
import com.epam.esm.restapibasics.api.hateoas.model.OrderListHateoasEntity;
import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.service.OrderService;
import com.epam.esm.restapibasics.service.dto.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
     * @return JSON {@link ResponseEntity} object that contains list of {@link OrderDto} objects
     */
    @GetMapping
    public ResponseEntity<OrderListHateoasEntity> getOrders(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer amount) {
        Paginator paginator = new Paginator(page, amount);
        List<OrderDto> orders = userId != null
                ? orderService.findByUser(userId, paginator)
                : orderService.findAll(paginator);

        OrderListHateoasAssembler orderListHateoasAssembler = new OrderListHateoasAssembler();
        OrderListHateoasEntity orderListHateoasEntity = orderListHateoasAssembler.toModel(orders);


        return new ResponseEntity<>(orderListHateoasEntity, HttpStatus.OK);
    }

    /**
     * Retrieve order by its unique id.
     *
     * @param id order id
     * @return JSON {@link ResponseEntity} object that contains {@link OrderHateoasEntity} object
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderHateoasEntity> getOrder(@PathVariable("id") long id) {
        OrderDto orderDto = orderService.findById(id);
        OrderHateoasAssembler orderHateoasAssembler = new OrderHateoasAssembler();
        OrderHateoasEntity orderHateoasEntity = orderHateoasAssembler.toModel(orderDto);
        return new ResponseEntity<>(orderHateoasEntity, HttpStatus.OK);
    }

    /**
     * Create an order.
     *
     * @param orderDto {@link OrderDto} instance (only {@code userId} and {@code certificateId} are required)
     * @return JSON {@link ResponseEntity} object that contains {@link OrderHateoasEntity} object
     */
    @PostMapping
    public ResponseEntity<OrderHateoasEntity> createOrder(@RequestBody OrderDto orderDto, Principal principal) {
        String username = principal.getName();
        OrderDto createdOrderDto = orderService.createOrder(orderDto, username);
        OrderHateoasAssembler orderHateoasAssembler = new OrderHateoasAssembler();
        OrderHateoasEntity orderHateoasEntity = orderHateoasAssembler.toModel(createdOrderDto);
        return new ResponseEntity<>(orderHateoasEntity, HttpStatus.CREATED);
    }
}
