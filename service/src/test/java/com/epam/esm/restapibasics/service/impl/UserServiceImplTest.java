package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.UserDao;
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
        User firstUser = new User();
        firstUser.setId(1L);
        firstUser.setName("user1");

        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setName("user2");

        User thirdUser = new User();
        thirdUser.setId(3L);
        thirdUser.setName("user3");

        return List.of(firstUser, secondUser, thirdUser);
    }

    private List<UserDto> provideUserDtoList() {
        UserDto firstDto = new UserDto();
        firstDto.setId(1L);
        firstDto.setName("user1");

        UserDto secondDto = new UserDto();
        secondDto.setId(2L);
        secondDto.setName("user2");

        UserDto thirdDto = new UserDto();
        thirdDto.setId(3L);
        thirdDto.setName("user3");

        return List.of(firstDto, secondDto, thirdDto);
    }
}
