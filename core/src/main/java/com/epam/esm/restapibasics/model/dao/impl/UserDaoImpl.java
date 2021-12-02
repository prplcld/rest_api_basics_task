package com.epam.esm.restapibasics.model.dao.impl;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.UserDao;
import com.epam.esm.restapibasics.model.entity.Tag;
import com.epam.esm.restapibasics.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private static final String SELECT_ALL_SQL = "SELECT u FROM User u";
    private static final String SELECT_BY_NAME_SQL = "SELECT u FROM User u WHERE u.username = :username";
    private static final String USERNAME_PARAM = "username";

    @PersistenceContext
    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll(Paginator paginator) {
        return entityManager.createQuery(SELECT_ALL_SQL, User.class)
                .setFirstResult(paginator.getStartValue())
                .setMaxResults(paginator.getAmount())
                .getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> userQuery = entityManager.createQuery(SELECT_BY_NAME_SQL, User.class);
        userQuery.setParameter(USERNAME_PARAM, username);
        return userQuery.getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }
}
