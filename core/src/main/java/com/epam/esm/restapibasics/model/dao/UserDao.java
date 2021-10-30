package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll(Paginator paginator);

    Optional<User> findById(Long id);
}
