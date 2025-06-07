package oth.ics.wtp.tulisajabackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import oth.ics.wtp.tulisajabackend.dtos.PostDto;
import oth.ics.wtp.tulisajabackend.entities.User;
import oth.ics.wtp.tulisajabackend.services.AuthService;
import oth.ics.wtp.tulisajabackend.services.PostService;

@RestController
public class PostController {
    private final PostService postService;
    private final AuthService authService;

    @Autowired
    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "{username}/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto createPost(@PathVariable String username, @RequestBody PostDto postDto) {
        User username = authService.getAuthenticatedUser()
    }
}
