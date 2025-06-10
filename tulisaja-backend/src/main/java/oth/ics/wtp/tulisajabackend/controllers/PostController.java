package oth.ics.wtp.tulisajabackend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import oth.ics.wtp.tulisajabackend.dtos.CreatePostDto;
import oth.ics.wtp.tulisajabackend.dtos.PostDto;
import oth.ics.wtp.tulisajabackend.entities.User;
import oth.ics.wtp.tulisajabackend.services.AuthService;
import oth.ics.wtp.tulisajabackend.services.PostService;

import java.util.List;

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
    @PostMapping(value = "posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto createPost(HttpServletRequest request, @RequestBody CreatePostDto createPost) {
        User user = authService.logIn(request);

        PostDto createdPost = postService.createPost(user.getUsername(), createPost);
        return createdPost;
    }

    @GetMapping(value = "posts/user/{username}")
    public List<PostDto> getUserPosts(@PathVariable String username) {
        return postService.getPostsByUsername(username);
    }

    @DeleteMapping(value = "posts/{id}")
    public void deletePost(@PathVariable long id, HttpServletRequest request) {
        User user = authService.logIn(request);
        postService.deletePost(user.getUsername(), id);
        ResponseEntity.ok("Post deleted sucessfully");
    }
}
