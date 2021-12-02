package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.entity.Role;

import java.util.Optional;

public interface RoleDao {
    Optional<Role> findByName(String name);
}
