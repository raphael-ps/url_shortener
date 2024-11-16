package io.github.raphael.url_shortener.repository;

import io.github.raphael.url_shortener.model.OriginalUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OriginalUrlRepository extends JpaRepository<OriginalUrl, String> {
    Optional<OriginalUrl> findByUrl(String url);
}
