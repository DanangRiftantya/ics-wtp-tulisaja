package services;

import dtos.CreateUserDto;
import dtos.UserDto;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.UserRepository;
import oth.ics.wtp.tulisajabackend.ClientErrors;
import oth.ics.wtp.tulisajabackend.WeakCrypto;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    private UserDto toDto(User user) {
        return new UserDto(user.getUsername());
    }

    private User toEntity(CreateUserDto createUser) {
        String hashedPassword = WeakCrypto.hashedPassword(createUser.password());
        return new User(createUser.name(), hashedPassword);
    }
}
