package io.github.raphael.url_shortener.model;

import io.github.raphael.url_shortener.dto.RequestUserPostDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.Instant;

@Getter
@Setter
@Table(name="user")
@Entity(name="user")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String email;
    private String password;
    private Instant createdAt;

    public User(RequestUserPostDTO newUser){
        this.email = newUser.email().trim();
        this.password = BCrypt.hashpw(newUser.password(), BCrypt.gensalt());
        this.username = newUser.username().trim();
        this.createdAt = Instant.now();
    }
}
