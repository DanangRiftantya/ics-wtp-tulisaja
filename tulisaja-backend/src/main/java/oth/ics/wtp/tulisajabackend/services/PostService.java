package oth.ics.wtp.tulisajabackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oth.ics.wtp.tulisajabackend.ClientErrors;
import oth.ics.wtp.tulisajabackend.dtos.CreatePostDto;
import oth.ics.wtp.tulisajabackend.dtos.PostDto;
import oth.ics.wtp.tulisajabackend.entities.Post;
import oth.ics.wtp.tulisajabackend.entities.User;
import oth.ics.wtp.tulisajabackend.repositories.PostRepository;
import oth.ics.wtp.tulisajabackend.repositories.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostDto createPost(String username, CreatePostDto createPost) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> ClientErrors.userNotFound(username));

        Post post = toEntity(createPost, user);

        postRepository.save(post);
        return toDto(post);
    }

    public List<PostDto> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> ClientErrors.userNotFound(username));

        return postRepository.findByUser(user).stream().map(this::toDto).collect(Collectors.toList());
    }

    public void deletePost(String username, long id) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> ClientErrors.userNotFound(username));

        Post post = postRepository.findByIdAndUser(id, user).orElseThrow(() -> ClientErrors.postNotFound(id));
        postRepository.delete(post);
    }

    private PostDto toDto(Post post) {
        return new PostDto(post.getId(), post.getPost(), post.getUsername(), post.getInstant(), post.getLikedCount());
    }

    private Post toEntity(CreatePostDto createPost, User user) {
        return new Post(createPost.post(), user, Instant.now());
    }
}
