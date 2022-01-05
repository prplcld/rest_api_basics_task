package com.epam.esm.restapibasics.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.epam.esm.restapibasics.api.exception.AuthorizationException;
import com.epam.esm.restapibasics.api.hateoas.UserHateoasAssembler;
import com.epam.esm.restapibasics.api.hateoas.model.UserHateoasEntity;
import com.epam.esm.restapibasics.service.UserService;
import com.epam.esm.restapibasics.service.dto.CredentialsDto;
import com.epam.esm.restapibasics.service.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class AuthController {

    private static final String ACCESS_TOKEN = "access-token";
    private static final String REFRESH_TOKEN = "refresh-token";
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String ROLES = "roles";
    private static final String secret = "secret";

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

    @GetMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(AUTHORIZATION);
        if (token != null) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                UserDto userDto = userService.findByUsername(username);

                String accessToken = JWT.create()
                        .withSubject(userDto.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(ROLES, userDto.getRole())
                        .sign(algorithm);



                Map<String, String> tokens = new HashMap<>();
                tokens.put(ACCESS_TOKEN, accessToken);
                tokens.put(REFRESH_TOKEN, token);
                response.setContentType(JSON_CONTENT_TYPE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                throw new AuthorizationException(e.getMessage());
            }
        } else {
            throw new AuthorizationException("refresh token missing");
        }
    }
}
