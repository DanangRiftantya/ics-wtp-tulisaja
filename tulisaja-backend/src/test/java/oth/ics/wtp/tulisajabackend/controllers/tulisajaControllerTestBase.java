package oth.ics.wtp.tulisajabackend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import oth.ics.wtp.tulisajabackend.WeakCrypto;
import oth.ics.wtp.tulisajabackend.entities.User;
import oth.ics.wtp.tulisajabackend.repositories.UserRepository;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class tulisajaControllerTestBase {
    protected static final String USER1 = "user1";
    protected static final String USER1_PASSWORD = "1234";
    protected static final String USER2 = "user2";
    protected static final String USER2_PASSWORD = "1234";

    @Autowired protected UserRepository userRepository;

    private final Map<String, HttpSession> session = new HashMap<>();

    @BeforeEach public void setUp() {
        createUser(USER1, USER1_PASSWORD);
        createUser(USER2, USER2_PASSWORD);
        session.clear();
    }

    private void createUser(String username, String password) {
        // create user in the app and store it in repo
        String passwordHash = WeakCrypto.hashedPassword(password);
        User user = new User(username, passwordHash);
        userRepository.save(user);
    }

    protected MockHttpServletRequest mockRequest(String username, String password) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        // restore sessionof same user, if any
        if (!session.containsKey(username)) {
            request.addHeader(HttpHeaders.AUTHORIZATION, basic(username, password));
            session.put(username, request.getSession());
        } else {
            request.setSession(session.get(username));
        }
        return request;
    }

    protected HttpServletRequest user1() {
        return mockRequest(USER1, USER1_PASSWORD);
    }
    protected HttpServletRequest user2() { return mockRequest(USER2, USER2_PASSWORD); }

    private String basic(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
    }
}
