package com.epam.esm.restapibasics.model.dao;

import com.epam.esm.restapibasics.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    /**
     * Retrieve all users from storage.
     *
     * @param paginator {@link Paginator} object with pagination logic
     * @return list of {@link User}
     */
    List<User> findAll(Paginator paginator);

    /**
     * Retrieve user by its unique id.
     *
     * @param id user id
     * @return {@link User} wrapped by {@link Optional}
     */
    Optional<User> findById(Long id);
}
