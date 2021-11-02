package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.UserDao;
import com.epam.esm.restapibasics.model.dao.exception.EntityNotFoundException;
import com.epam.esm.restapibasics.model.entity.User;
import com.epam.esm.restapibasics.service.UserService;
import com.epam.esm.restapibasics.service.dto.UserDto;
import com.epam.esm.restapibasics.service.dto.util.DtoMappingUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> findAll(Paginator paginator) {
        return userDao.findAll(paginator)
                .stream()
                .map(DtoMappingUtil::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        User user = userDao.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        return DtoMappingUtil.mapToUserDto(user);
    }
}
