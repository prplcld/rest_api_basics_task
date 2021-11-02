package com.epam.esm.restapibasics.model.dao.impl;

import com.epam.esm.restapibasics.model.dao.OrderDao;
import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {

    private static final String ID = "id";

    private static final String SELECT_ALL = "SELECT o FROM Order o";
    private static final String SELECT_BY_USER = "SELECT o FROM Order o WHERE o.user.id = :id";

    @PersistenceContext
    private final  EntityManager entityManager;

    public OrderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> findAll(Paginator paginator) {
        return entityManager.createQuery(SELECT_ALL, Order.class)
                .setFirstResult(paginator.getStartValue())
                .setMaxResults(paginator.getAmount())
                .getResultList();
    }

    @Override
    public Optional<Order> findById(long id) {
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findByUser(Paginator paginator, long userId) {
        TypedQuery<Order> orderQuery = entityManager.createQuery(SELECT_BY_USER, Order.class);
        orderQuery.setParameter(ID, userId);

        return orderQuery.setFirstResult(paginator.getStartValue())
                .setMaxResults(paginator.getAmount())
                .getResultList();
    }

    @Override
    public Long create(Order order) {
        entityManager.persist(order);
        return order.getId();
    }
}
