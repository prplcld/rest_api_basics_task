package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.UserDao;
import com.epam.esm.restapibasics.model.entity.Role;
import com.epam.esm.restapibasics.model.entity.User;
import com.epam.esm.restapibasics.service.dto.UserDto;
import com.epam.esm.restapibasics.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(UserServiceImplTest.class);
    }


    @Test
    void testFindAll() {
        Paginator paginator = new Paginator(null, null);
        when(userDao.findAll(paginator)).thenReturn(provideUsers());

        List<UserDto> expectedDtoList = provideUserDtoList();
        List<UserDto> actualDtoList = userService.findAll(paginator);

        assertEquals(expectedDtoList, actualDtoList);
    }

    @Test
    void testFindById() {
        User user = provideUsers().get(0);
        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));

        UserDto expectedDto = provideUserDtoList().get(0);
        UserDto actualDto = userService.findById(user.getId());

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testFindByIdWhenUserNotFound() {
        long userId = 1;
        when(userDao.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(userId));
    }


    private List<User> provideUsers() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        User firstUser = new User();
        firstUser.setId(1L);
        firstUser.setUsername("user1");
        firstUser.setEmail("email1");
        firstUser.setPassword("123");
        firstUser.setRole(role);

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setUsername("user2");
        secondUser.setEmail("email2");
        secondUser.setPassword("123");
        secondUser.setRole(role);

        User thirdUser = new User();
        thirdUser.setId(3L);
        thirdUser.setUsername("user3");
        thirdUser.setEmail("email3");
        thirdUser.setPassword("123");
        thirdUser.setRole(role);

        return List.of(firstUser, secondUser, thirdUser);
    }

    private List<UserDto> provideUserDtoList() {
        UserDto firstDto = new UserDto();
        firstDto.setId(1L);
        firstDto.setUsername("user1");
        firstDto.setEmail("email1");
        firstDto.setRole("ADMIN");

        UserDto secondDto = new UserDto();
        secondDto.setId(2L);
        secondDto.setUsername("user2");
        secondDto.setEmail("email2");
        secondDto.setRole("ADMIN");

        UserDto thirdDto = new UserDto();
        thirdDto.setId(3L);
        thirdDto.setUsername("user3");
        thirdDto.setEmail("email3");
        thirdDto.setRole("ADMIN");

        return List.of(firstDto, secondDto, thirdDto);
    }
}
