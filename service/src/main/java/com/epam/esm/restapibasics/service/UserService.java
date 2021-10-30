package com.epam.esm.restapibasics.service;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.service.dto.UserDto;

import java.util.List;

public interface UserService {
    public List<UserDto> findAll(Paginator paginator);

    public UserDto findById(Long id);
}
