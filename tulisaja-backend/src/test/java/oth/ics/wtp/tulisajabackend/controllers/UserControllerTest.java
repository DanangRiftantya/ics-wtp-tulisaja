package oth.ics.wtp.tulisajabackend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import oth.ics.wtp.tulisajabackend.dtos.CreateUserDto;
import oth.ics.wtp.tulisajabackend.dtos.UserDto;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest extends tulisajaControllerTestBase {
    @Autowired private UserController controller;
    @Autowired private PostController postController;

    @Test
    public void testCreateUserLoginLogout() {
        controller.createUser(new CreateUserDto("user123", "password123"));
        controller.logIn(mockRequest("user123", "password123"));
        controller.logOut(mockRequest("user123", "password123"));
    }

    @Test
    public void testCreateGet() {
        controller.createUser(new CreateUserDto("user1234", "password123"));
        UserDto user = controller.getUser(user1(), "user1234");
        assertEquals("user1234", user.name());
    }

    @Test
    public void testFollowUserList() {
        controller.followUser("user123", "user1234");
        List<UserDto> following = controller.listFollowing("user123");
        assertEquals(1, following.size());
        assertEquals("user1234", following.get(0).name());

//        List<UserDto> follower = controller.listFollowing("user1234");
//        assertEquals(1, follower.size());
//        assertEquals("user1234", follower.get(0).name());
    }

//    @Test
//    public void testUnfollowUser() {
//        controller.unfollowUser("user123", "user1234");
//        List<UserDto> following = controller.listFollowing("user123");
//        assertEquals(0, following.size());
//    }
}
