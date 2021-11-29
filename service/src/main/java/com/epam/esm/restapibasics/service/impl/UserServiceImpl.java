package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.UserDao;
import com.epam.esm.restapibasics.model.entity.User;
import com.epam.esm.restapibasics.service.UserService;
import com.epam.esm.restapibasics.service.dto.UserDto;
import com.epam.esm.restapibasics.service.dto.util.DtoMappingUtil;
import com.epam.esm.restapibasics.service.exception.EntityNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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
        User user = userDao.findById(id).orElseThrow(() -> new EntityNotFoundException(id, User.class));
        return DtoMappingUtil.mapToUserDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByUsername(username);
        if (user.isPresent()) {
            User u = user.get();
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(u.getRole().getName()));
            return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), authorities);
        }
        else {
            throw new EntityNotFoundException(User.class);
        }
    }
}
