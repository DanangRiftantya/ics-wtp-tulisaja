package controllers;

import dtos.CreateUserDto;
import dtos.UserDto;
import entities.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import services.AuthService;
import services.UserService;

@RestController
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping(value = "user/logout")
    public void logOut(HttpServletRequest request) {
        authService.logOut(request);
    }
}
