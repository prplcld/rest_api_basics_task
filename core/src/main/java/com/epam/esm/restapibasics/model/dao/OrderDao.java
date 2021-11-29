package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    /**
     * Retrieve all orders from storage.
     *
     * @param paginator {@link Paginator} object with pagination logic
     * @return list of {@link Order}
     */
    List<Order> findAll(Paginator paginator);

    /**
     * Retrieve order by its unique id.
     *
     * @param id order id
     * @return {@link Order} wrapped by {@link Optional}
     */
    Optional<Order> findById(long id);

    /**
     * Retrieve all orders of specified user.
     *
     * @param paginator {@link Paginator} object with pagination logic
     * @return list of {@link Order}
     */
    List<Order> findByUser(Paginator paginator, long userId);

    /**
     * Create a new order in the storage.
     *
     * @param order {@link Order} instance
     * @return created {@link Order}
     */
    Order create(Order order);
}
