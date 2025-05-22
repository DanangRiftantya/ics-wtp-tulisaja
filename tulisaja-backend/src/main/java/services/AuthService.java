package services;

import entities.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import oth.ics.wtp.tulisajabackend.ClientErrors;
import oth.ics.wtp.tulisajabackend.WeakCrypto;
import repositories.UserRepository;

import java.util.Optional;

@Service
public class AuthService {
    private static final String SESSION_USER_NAME = "tulisaja-session-username";
    private final UserRepository userRepository;

    @Autowired
    private AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthenticatedUser(HttpServletRequest request) {
        Object sessionUsername = request.getSession().getAttribute(SESSION_USER_NAME);
        if (sessionUsername instanceof String username) {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                return user.get();
            }
            else {
                logOut(request);
                throw ClientErrors.unauthorized();
            }
        }
        return logIn(request);
    }

    public User logIn(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String decoded = WeakCrypto.base64decode(authHeader.substring(authHeader.indexOf(' ') + 1));
            String[] parts = decoded.split(":");
            String username = parts[0];
            String password = parts[1];
            String hashedPassword = WeakCrypto.hashedPassword(password);
            User user = userRepository.findByUsername(username).orElseThrow();
            if (!user.getHashedPassword().equals(hashedPassword)) {
                throw new Exception();
            }
            request.getSession().setAttribute(SESSION_USER_NAME, username);
            return user;
        }
        catch (Exception e) {
            logOut(request);
            throw ClientErrors.unauthorized();
        }
    }

    public void logOut(HttpServletRequest request) {
        request.getSession().setAttribute(SESSION_USER_NAME, null);
    }
}
