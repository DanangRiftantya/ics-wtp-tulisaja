package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import org.hibernate.annotations.OnDelete;

import java.util.List;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
public class User {
    @Id
    private String username;
    private String hashedPassword;

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

}
