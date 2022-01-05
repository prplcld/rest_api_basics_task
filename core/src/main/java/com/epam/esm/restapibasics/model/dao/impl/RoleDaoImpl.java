package com.epam.esm.restapibasics.model.dao.impl;

import com.epam.esm.restapibasics.model.dao.RoleDao;
import com.epam.esm.restapibasics.model.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class RoleDaoImpl implements RoleDao {

    private static final String SELECT_BY_NAME = "SELECT r FROM Role r WHERE r.name = :name";
    private static final String NAME_PARAMETER = "name";

    @PersistenceContext
    private final EntityManager entityManager;

    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Role> findByName(String name) {
        TypedQuery<Role> roleQuery = entityManager.createQuery(SELECT_BY_NAME, Role.class);
        roleQuery.setParameter(NAME_PARAMETER, name);
        return roleQuery.getResultList()
                .stream()
                .findFirst();
    }
}
