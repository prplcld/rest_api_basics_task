package com.epam.esm.restapibasics.service;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.service.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll(Paginator paginator);

    List<OrderDto> findByUser(Long userId, Paginator paginator);

    OrderDto findById(Long id);

    OrderDto createOrder(OrderDto orderDto);
}
