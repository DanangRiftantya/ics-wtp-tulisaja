package oth.ics.wtp.tulisajabackend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import java.time.Instant;
import java.util.List;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
public class Post {
    @Id @GeneratedValue
    private long id;

    private String post;

    @ManyToOne
    @OnDelete(action = CASCADE)
    private User user;

    private Instant creationTime = Instant.now();

    @ManyToMany
    @JoinTable(name = "post_likes", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likedBy;

    public Post() {}

    public Post(String post, User user, Instant creationTime) {
        this.post = post;
        this.user = user;
        this.creationTime = creationTime;
    }

    public long getId() {
        return id;
    }

    public String getPost() {
        return post;
    }

    public List<User> getLikedBy() {return likedBy;}

    public User getUsername() {return user;}

    public int getLikedCount() {return likedBy.size();}

    public Instant getInstant() {return creationTime;}
}
