package repositories;

import entities.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByUsername(String username);
    Optional<Post> findByPostIdAndUsername(Long postId, String username);
}
