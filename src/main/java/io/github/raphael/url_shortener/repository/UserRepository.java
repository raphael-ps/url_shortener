package io.github.raphael.url_shortener.repository;

import io.github.raphael.url_shortener.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
