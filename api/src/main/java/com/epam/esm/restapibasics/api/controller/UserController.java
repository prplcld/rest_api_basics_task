package com.epam.esm.restapibasics.api.controller;

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

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(@RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) Integer amount) {
        List<UserDto> users = userService.findAll(new Paginator(page, amount));
        return new ResponseEntity<>(users, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        UserDto userDto = userService.findById(id);
        return new ResponseEntity<>(userDto, OK);
    }
}
