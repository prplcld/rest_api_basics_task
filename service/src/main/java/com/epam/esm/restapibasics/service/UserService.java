package com.epam.esm.restapibasics.service;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.service.dto.UserDto;

import java.util.List;

public interface UserService {
    /**
     * Retrieve all users.
     *
     * @param paginator {@link Paginator} object with pagination logic
     * @return list of {@link UserDto}
     */
    List<UserDto> findAll(Paginator paginator);

    /**
     * Retrieve user by its unique id.
     *
     * @param id user id
     * @return {@link UserDto} object
     */
    UserDto findById(Long id);
}
