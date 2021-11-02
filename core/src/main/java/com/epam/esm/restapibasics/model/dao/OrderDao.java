package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> findAll(Paginator paginator);

    Optional<Order> findById(long id);

    List<Order> findByUser(Paginator paginator, long userId);

    Long create(Order order);
}
