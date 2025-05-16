package dtos;

import entities.Post;
import org.hibernate.mapping.List;

public record UserDto(String name/*List <Post> post*/) {
}
