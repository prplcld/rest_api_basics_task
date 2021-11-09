package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.api.hateoas.HateoasEntity;
import com.epam.esm.restapibasics.api.hateoas.HateoasListEntity;
import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.service.UserService;
import com.epam.esm.restapibasics.service.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieve all users.
     *
     * @return JSON {@link ResponseEntity} object that contains list of {@link HateoasListEntity} objects
     */
    @GetMapping
    public ResponseEntity<HateoasListEntity<UserDto>> getAll(@RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer amount) {
        List<UserDto> users = userService.findAll(new Paginator(page, amount));
        HateoasListEntity<UserDto> hateoasListEntity = HateoasListEntity.build(users, UserController.class);
        return new ResponseEntity<>(hateoasListEntity, OK);
    }

    /**
     * Retrieve user by its unique id.
     *
     * @param id user id
     * @return JSON {@link ResponseEntity} object that contains {@link HateoasEntity} object
     */
    @GetMapping("/{id}")
    public ResponseEntity<HateoasEntity<UserDto>> getUser(@PathVariable("id") Long id) {
        UserDto userDto = userService.findById(id);
        HateoasEntity<UserDto> hateoasEntity = HateoasEntity.build(userDto);
        return new ResponseEntity<>(hateoasEntity, OK);
    }
}
