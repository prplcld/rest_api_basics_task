package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.api.hateoas.UserHateoasAssembler;
import com.epam.esm.restapibasics.api.hateoas.UserListHateoasAssembler;
import com.epam.esm.restapibasics.api.hateoas.model.UserHateoasEntity;
import com.epam.esm.restapibasics.api.hateoas.model.UserListHateoasEntity;
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
     * @return JSON {@link ResponseEntity} object that contains list of {@link List<UserDto>} objects
     */
    @GetMapping
    public ResponseEntity<UserListHateoasEntity> getAll(@RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer amount) {
        List<UserDto> users = userService.findAll(new Paginator(page, amount));

        UserListHateoasAssembler userListHateoasAssembler = new UserListHateoasAssembler();
        UserListHateoasEntity userListHateoasEntity = userListHateoasAssembler.toModel(users);
        return new ResponseEntity<>(userListHateoasEntity, OK);
    }

    /**
     * Retrieve user by its unique id.
     *
     * @param id user id
     * @return JSON {@link ResponseEntity} object that contains {@link UserHateoasEntity} object
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserHateoasEntity> getUser(@PathVariable("id") Long id) {
        UserDto userDto = userService.findById(id);
        UserHateoasAssembler userHateoasAssembler = new UserHateoasAssembler();
        UserHateoasEntity userHateoasEntity = userHateoasAssembler.toModel(userDto);
        return new ResponseEntity<>(userHateoasEntity, OK);
    }
}
