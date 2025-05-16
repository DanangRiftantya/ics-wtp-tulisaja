package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import java.time.Instant;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
public class Post {
    @Id @GeneratedValue private long id;
    private String post;
    @ManyToOne @OnDelete(action = CASCADE) private User user;
    private Instant creationTime;

    public Post() {}

    public Post(String post) {
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public String getPost() {
        return post;
    }
}
