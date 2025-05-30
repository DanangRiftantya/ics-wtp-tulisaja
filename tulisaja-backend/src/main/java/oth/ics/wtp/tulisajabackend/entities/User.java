package oth.ics.wtp.tulisajabackend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static jakarta.persistence.FetchType.EAGER;

@Entity
public class User {
    @Id
    private String username;

    private String hashedPassword;

    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = CascadeType.ALL)
    List <Post> post;

    // User that this user is following
    @ManyToMany
    @JoinTable(name = "user_following",
                joinColumns = @JoinColumn(name = "follower_id"),
                inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private List<User> following = new ArrayList<>();

    // Users who follow this user
    @ManyToMany(mappedBy = "following")
    private List<User> followers = new ArrayList<>();

    public User() {}

    public User (String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Post> getPosts(){
        return post;
    }

    public List<User> getFollowing() {
        return following;
    }

    public List<User> getFollowers() {
        return followers;
    }
}
