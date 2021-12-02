package com.epam.esm.restapibasics.api.controller;

import com.epam.esm.restapibasics.api.hateoas.UserHateoasAssembler;
import com.epam.esm.restapibasics.api.hateoas.model.UserHateoasEntity;
import com.epam.esm.restapibasics.service.UserService;
import com.epam.esm.restapibasics.service.dto.CredentialsDto;
import com.epam.esm.restapibasics.service.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserHateoasEntity> register(@RequestBody CredentialsDto credentials) {
        UserDto userDto = userService.register(credentials);
        UserHateoasAssembler userHateoasAssembler = new UserHateoasAssembler();
        UserHateoasEntity userHateoasEntity = userHateoasAssembler.toModel(userDto);
        return new ResponseEntity<>(userHateoasEntity, OK);
    }


}
