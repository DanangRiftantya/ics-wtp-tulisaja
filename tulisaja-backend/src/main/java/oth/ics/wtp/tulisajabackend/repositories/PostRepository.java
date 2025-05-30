package oth.ics.wtp.tulisajabackend.repositories;

import org.springframework.stereotype.Repository;
import oth.ics.wtp.tulisajabackend.entities.Post;
import org.springframework.data.repository.CrudRepository;
import oth.ics.wtp.tulisajabackend.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByUser(User user);
    Optional<Post> findByIdAndUser(long id, User user);
}
