package entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import static jakarta.persistence.FetchType.EAGER;

@Entity
public class User {
    @Id private String username;
    private String hashedPassword;
    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = CascadeType.ALL) List <Post> post;

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
}
