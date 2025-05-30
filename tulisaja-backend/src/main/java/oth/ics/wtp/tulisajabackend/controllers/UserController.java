package oth.ics.wtp.tulisajabackend.controllers;

import oth.ics.wtp.tulisajabackend.dtos.CreateUserDto;
import oth.ics.wtp.tulisajabackend.dtos.UserDto;
import oth.ics.wtp.tulisajabackend.entities.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import oth.ics.wtp.tulisajabackend.services.AuthService;
import oth.ics.wtp.tulisajabackend.services.UserService;

@RestController
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody CreateUserDto createUser) {
        return userService.create(createUser);
    }

    @SecurityRequirement(name = "basicAuth")
    @PostMapping(value = "users/login")
    public UserDto logIn(HttpServletRequest request) {
        User user = authService.logIn(request);
        return userService.get(user.getUsername());
    }

    @SecurityRequirement(name = "basicAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "users/logout")
    public void logOut(HttpServletRequest request) {
        authService.logOut(request);
    }

    @GetMapping(value = "users/search/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto searchUser(@PathVariable String username) {
        return userService.get(username);
    }
}
