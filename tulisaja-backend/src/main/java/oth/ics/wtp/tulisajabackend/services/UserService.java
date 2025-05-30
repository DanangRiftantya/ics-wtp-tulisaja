package oth.ics.wtp.tulisajabackend.services;

import oth.ics.wtp.tulisajabackend.dtos.CreateUserDto;
import oth.ics.wtp.tulisajabackend.dtos.UserDto;
import oth.ics.wtp.tulisajabackend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oth.ics.wtp.tulisajabackend.repositories.UserRepository;
import oth.ics.wtp.tulisajabackend.ClientErrors;
import oth.ics.wtp.tulisajabackend.WeakCrypto;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> list(){
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).map(this::toDto).toList();
    }

    public UserDto create(CreateUserDto createUser) {
        if (createUser.name() == null || createUser.name().isEmpty() ||
            createUser.password() == null || createUser.password().isEmpty()) {
            throw ClientErrors.invalidCredentials();
        }
        if (userRepository.existsByUsername(createUser.name())) {
            throw ClientErrors.userNameTaken(createUser.name());
        }

        User user = toEntity(createUser);
        userRepository.save(user);
        return toDto(user);
    }

    public UserDto get(String username) {
        return userRepository.findByUsername(username)
                .map(this::toDto)
                .orElseThrow(() -> ClientErrors.userNotFound(username));
    }

    public void followUser(String followerUsername, String followingUsername) {
        if (followerUsername.equals(followingUsername)) {
            throw ClientErrors.userCannotFollowOwn(followerUsername);
        }

        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> ClientErrors.userNotFound(followerUsername));
        User following = userRepository.findByUsername(followingUsername)
                .orElseThrow(() -> ClientErrors.userNotFound(followingUsername));

        if (!follower.getFollowing().contains(following)) {
            follower.getFollowing().add(following);
            userRepository.save(follower);
        }
    }

    public void unfollowUser(String followerUsername, String followingUsername) {
        if (followerUsername.equals(followingUsername)) {
            throw ClientErrors.userCannotFollowOwn(followerUsername);
        }

        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> ClientErrors.userNotFound(followerUsername));
        User following = userRepository.findByUsername(followingUsername)
                .orElseThrow(() -> ClientErrors.userNotFound(followingUsername));

        if (follower.getFollowing().contains(following)) {
            follower.getFollowing().remove(following);
            userRepository.save(follower);
        }
    }

    public List<UserDto> listFollowers(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> ClientErrors.userNotFound(username));

        return user.getFollowers().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<UserDto> listFollowing(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> ClientErrors.userNotFound(username));

        return user.getFollowing().stream().map(this::toDto).collect(Collectors.toList());
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getUsername());
    }

    private User toEntity(CreateUserDto createUser) {
        String hashedPassword = WeakCrypto.hashedPassword(createUser.password());
        return new User(createUser.name(), hashedPassword);
    }
}
