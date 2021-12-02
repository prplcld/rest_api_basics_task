package com.epam.esm.restapibasics.service;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.service.dto.OrderDto;

import java.security.Principal;
import java.util.List;

public interface OrderService {
    /**
     * Retrieve all orders.
     *
     * @param paginator {@link Paginator} object with pagination logic
     * @return list of {@link OrderDto}
     */
    List<OrderDto> findAll(Paginator paginator);

    /**
     * Retrieve all orders of specified user.
     *
     * @param userId user id
     * @param paginator {@link Paginator} object with pagination logic
     * @return list of {@link OrderDto}
     */
    List<OrderDto> findByUser(Long userId, Paginator paginator);

    /**
     * Retrieve order by its unique id.
     *
     * @param id order id
     * @return {@link OrderDto} object
     */
    OrderDto findById(Long id);

    /**
     * Make an order.
     *
     * @param orderDto {@link OrderDto} instance (only {@code userId} and {@code certificateId} are required)
     * @return {@link OrderDto} object that represents created order
     */
    OrderDto createOrder(OrderDto orderDto, String username);
}
