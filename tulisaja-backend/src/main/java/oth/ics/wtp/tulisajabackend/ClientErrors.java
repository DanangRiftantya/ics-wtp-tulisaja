package oth.ics.wtp.tulisajabackend;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientErrors {
    private static final Logger logger = LoggerFactory.getLogger(ClientErrors.class);

    public static ResponseStatusException userNameTaken(String name){
        return log(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken: " + name));
    }

    public static ResponseStatusException invalidCredentials() {
        return log(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials"));
    }

    private static ResponseStatusException log(ResponseStatusException e) {
        logger.error(ExceptionUtils.getMessage(e) + "\n" + ExceptionUtils.getStackTrace(e));
        return e;
    }

    public static ResponseStatusException userNotFound(String username) {
        return log(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + username));
    }

    public static ResponseStatusException unauthorized() {
        return log(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    }

    public static ResponseStatusException userCannotFollowOwn(String followerUsername) {
        return log(new ResponseStatusException(HttpStatus.FORBIDDEN, "User cannot follow or unfollow themselves"));
    }

    public static ResponseStatusException postNotFound(long id) {
        return log(new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found: " + id));
    }
}
